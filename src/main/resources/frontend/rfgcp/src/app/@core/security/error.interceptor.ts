import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Injectable} from '@angular/core';
import {catchError} from 'rxjs/operators';
import {ToastrService} from 'ngx-toastr';
import {UsuarioService} from '../service/usuario.service';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private usuarioService: UsuarioService,
              private toast: ToastrService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError(err => {
      /**
       * Se o cara ter um erro 401, desloga ele e manda, e recarrega a página, o que vai mandá-lo pra
       * tela de login.
       */
      if (err.status === 401 && !request.url.includes('/auth')) {
        this.usuarioService.redirectLogin();
      } else if ((err.status === 401 || err.status === 403) && request.url.includes('/auth')) {
        this.toast.error('Usuário ou senha incorretos.', 'Identificação inválida');
      } else if (err.status === 0) {
        this.toast.error('Não foi possível comunicar-se com o servidor.', 'Erro de conexão');
      }
      let error;
      if (err.error) {
        error = err.error.message;
      } else {
        error = err.statusText;
      }
      return throwError(error);
    }));
  }

}
