import {Component, OnInit, Renderer2} from '@angular/core';

declare const $: any;

@Component({
  selector: 'app-aside-menu',
  templateUrl: './aside-menu.component.html',
  styleUrls: ['./aside-menu.component.css']
})
export class AsideMenuComponent implements OnInit {

  constructor(private renderer: Renderer2) {
  }

  ngOnInit() {
    const dropdown = document.getElementsByClassName('dropdown-btn');
    let i;
    for (i = 0; i < dropdown.length; i++) {
      dropdown[i].addEventListener('click', function() {
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
      item[i].addEventListener('click', function() {
        document.querySelectorAll('.sidenav a').forEach(asd => {
          asd.classList.remove('item-ativo');
        });
        this.classList.toggle('item-ativo');
      });
    }
  }

}
