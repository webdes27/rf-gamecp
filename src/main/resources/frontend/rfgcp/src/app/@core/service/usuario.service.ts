import {Injectable} from '@angular/core';
import {AbstractService} from './abstract.service';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Usuario} from '../model/usuario.model';
import {map} from 'rxjs/operators';
import {ApiResponse} from '../model/api-response.model';
import {Router} from '@angular/router';
import {AlterarSenha} from '../model/payloads/alterar-senha.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService extends AbstractService {

  private BASE_URL = this.API_REST + '/usuario/';

  constructor(http: HttpClient, private router: Router) {
    super(http);
  }

  login(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(this.API_REST + '/auth/login', usuario).pipe(
      map(resp => {
        this.salvarUsuario(resp);
        return resp;
      }));
  }

  registrar(usuario: Usuario): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.BASE_URL, usuario);
  }

  alterarSenha(alterarSenha: AlterarSenha): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.BASE_URL + 'alterar-senha', alterarSenha);
  }

  recuperarFireguard(usuario: Usuario) {
    return this.http.post<ApiResponse>(this.BASE_URL + 'fireguard', usuario);
  }

  logout(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.API_REST + '/auth/logout').pipe(
      map(resp => {
        this.removeUsuario();
        this.redirectLogin();
        return resp;
      }));
  }

  salvarUsuario(usuario) {
    localStorage.setItem('usuarioLogado', JSON.stringify(usuario));
  }

  getUsuarioLogado(): Usuario {
    return JSON.parse(localStorage.getItem('usuarioLogado'));
  }

  removeUsuario() {
    localStorage.removeItem('usuarioLogado');
  }

  redirectLogin() {
    this.router.navigate(['']);
  }

  redirectPrincipal() {
    this.router.navigate(['../../principal']);
  }
}
