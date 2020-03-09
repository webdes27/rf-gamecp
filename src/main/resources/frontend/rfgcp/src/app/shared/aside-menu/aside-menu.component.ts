import {Component, OnDestroy, OnInit, Renderer2} from '@angular/core';
import {UsuarioService} from "../../@core/service/usuario.service";
import {takeWhile} from "rxjs/operators";

declare const $: any;

@Component({
  selector: 'app-aside-menu',
  templateUrl: './aside-menu.component.html',
  styleUrls: ['./aside-menu.component.css']
})
export class AsideMenuComponent implements OnInit, OnDestroy {

  private alive = true;

  constructor(private usuarioService: UsuarioService) {
  }

  ngOnInit() {
    const dropdown = document.getElementsByClassName('dropdown-btn');
    let i;
    for (i = 0; i < dropdown.length; i++) {
      dropdown[i].addEventListener('click', function () {
        this.classList.toggle('active');
        const dropdownContent = this.nextElementSibling;
        if (dropdownContent.style.display === 'block') {
          dropdownContent.style.display = 'none';
        } else {
          dropdownContent.style.display = 'block';
        }
      });
    }

    const item = document.querySelectorAll('.sidenav a');

    for (i = 0; i < item.length; i++) {
      item[i].addEventListener('click', function () {
        document.querySelectorAll('.sidenav a').forEach(asd => {
          asd.classList.remove('item-ativo');
        });
        this.classList.toggle('item-ativo');
      });
    }
  }

  logout() {
    this.usuarioService.logout()
      .pipe(takeWhile(() => this.alive))
      .subscribe()
  }

  ngOnDestroy(): void {
  }

}
