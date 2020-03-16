import {Component, Input, OnInit, Renderer2, ViewEncapsulation} from '@angular/core';
import {Equipamento, Personagem, Talica} from '../../@core/model/personagem.model';
import {takeWhile} from 'rxjs/operators';

declare const $: any;

@Component({
  selector: 'app-detalhe-personagem',
  templateUrl: './detalhe-personagem.component.html',
  styleUrls: ['./detalhe-personagem.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class DetalhePersonagemComponent implements OnInit {

  @Input() personagem: Personagem;
  @Input() pessoal = true;
  @Input() grid = true;
  private equipeDetalhe: Equipamento;

  constructor() {
  }

  ngOnInit() {
  }

  mostraDetalhes(event, enter: boolean, equip: Equipamento = null) {
    const detalhe = $('.detalhe-item');
    if (this.equipeDetalhe != equip) {
      if (enter) {
        // const bb = detalhe.getBoundingClientRect();
        detalhe.css({top: event.target.offsetParent.offsetTop + 32, left: event.target.offsetParent.offsetLeft + 64});
      }
    }
    this.equipeDetalhe = equip;
    detalhe.toggleClass("d-none");
  }

  getTalica(nomeTalica) {
    const s = Object.keys(Talica).find(t => t === nomeTalica);
    return Talica[nomeTalica];
  }
}
