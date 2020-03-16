import {Component, OnDestroy, OnInit} from '@angular/core';
import {ServerService} from "../../@core/service/server.service";
import {takeWhile} from "rxjs/operators";
import {Concelhos} from "../../@core/model/concelhos.model";

@Component({
  selector: 'app-lista-concelho',
  templateUrl: './lista-concelho.component.html',
  styleUrls: ['./lista-concelho.component.scss']
})
export class ListaConcelhoComponent implements OnInit, OnDestroy {

  private alive = true;
  private concelhos: Concelhos;

  constructor(private serverService: ServerService) { }

  ngOnInit() {
    this.serverService.getConcelhos()
      .pipe(takeWhile(() => this.alive))
      .subscribe(c => {
        this.concelhos = c;
      })
  }

  ngOnDestroy(): void {
    this.alive = false;
  }
}
