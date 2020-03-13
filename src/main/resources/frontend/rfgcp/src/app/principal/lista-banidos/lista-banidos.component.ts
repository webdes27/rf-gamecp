import {Component, OnDestroy, OnInit} from '@angular/core';
import {Banido} from "../../@core/model/banido.model";
import {PersonagemService} from "../../@core/service/personagem.service";
import {takeWhile} from "rxjs/operators";

@Component({
  selector: 'app-lista-banidos',
  templateUrl: './lista-banidos.component.html',
  styleUrls: ['./lista-banidos.component.scss']
})
export class ListaBanidosComponent implements OnInit, OnDestroy {

  private banidos: Banido[];
  private alive = true;
  private nome: string;

  constructor(private personagemService: PersonagemService) {
    this.banidos = [];
  }

  ngOnInit() {
    this.submit();
  }

  ngOnDestroy(): void {
    this.alive = false;
  }

  submit() {
    this.personagemService.getBanidos(this.nome)
      .pipe(takeWhile(() => this.alive))
      .subscribe(lista => {
        this.banidos = lista;
      });
  }
}
