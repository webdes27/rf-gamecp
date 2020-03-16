import {Component, OnDestroy, OnInit} from '@angular/core';
import {Banido} from "../../@core/model/banido.model";
import {PersonagemService} from "../../@core/service/personagem.service";
import {takeWhile} from "rxjs/operators";
import {ServerService} from "../../@core/service/server.service";

@Component({
  selector: 'app-lista-banidos',
  templateUrl: './lista-banidos.component.html',
  styleUrls: ['./lista-banidos.component.scss']
})
export class ListaBanidosComponent implements OnInit, OnDestroy {

  private banidos: Banido[];
  private alive = true;

  constructor(private serverService: ServerService) {}

  ngOnInit() {
    this.submit();
  }

  ngOnDestroy(): void {
    this.alive = false;
  }

  submit(nome = "") {
    console.log(nome);
    this.serverService.getBanidos(nome)
      .pipe(takeWhile(() => this.alive))
      .subscribe(lista => {
        this.banidos = lista;
      });
  }

  getEndDate(periodo: number, dt: string) {
    const dtInicial: Date = new Date(dt.split('T').join(','));
    const dtFinal: Date = new Date();
    dtFinal.setSeconds(dtInicial.getSeconds() + (periodo * 3600));
    return dtFinal.toLocaleString('pt-BR');
  }

  formatDate(dataInicio: Date) {
    return new Date(dataInicio.toString().split('T').join(',')).toLocaleString('pt-BR');
  }

  formataNome(nomeUsuario: string) {
    return nomeUsuario.replace(/\u0000/g,'')
  }
}
