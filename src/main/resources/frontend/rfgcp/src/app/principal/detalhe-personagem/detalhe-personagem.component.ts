import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {Equipamento, Personagem} from '../../@core/model/personagem.model';
import {takeWhile} from 'rxjs/operators';

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

  constructor() {
  }

  ngOnInit() {
  }

}
