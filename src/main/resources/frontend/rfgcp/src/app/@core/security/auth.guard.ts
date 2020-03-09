import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';

import {UsuarioService} from '../service/usuario.service';

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  constructor(private usuarioService: UsuarioService, private router: Router) {
  }

  canActivate(activeRoute: ActivatedRouteSnapshot, routerState: RouterStateSnapshot): boolean {
    const logado = this.usuarioService.getUsuarioLogado();
    if (!logado) {
      if (routerState.url.includes('auth')) {
        return true;
      }
      this.usuarioService.redirectLogin();
    } else {
      return true;
    }
  }
}
