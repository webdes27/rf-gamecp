import {Component, OnDestroy, OnInit} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {UsuarioService} from '../../@core/service/usuario.service';
import {Equipamento, Melhoria, Personagem, Raca, Talica} from '../../@core/model/personagem.model';
import {PersonagemService} from '../../@core/service/personagem.service';
import '../../@core/model/extensions/seconds-converter.extensions';

declare const $: any;

@Component({
  selector: 'app-personagens',
  templateUrl: './personagens.component.html',
  styleUrls: ['./personagens.component.scss']
})
export class PersonagensComponent implements OnInit, OnDestroy {

  private alive: boolean;
  private personagens: Personagem[];
  personagemDetalhe: Personagem;

  constructor(private toastr: ToastrService, private usuarioService: UsuarioService, private personagemService: PersonagemService) {
    this.alive = true;
    this.personagemDetalhe = new Personagem();
  }

  ngOnInit() {
    const p = new Personagem("FuMassa", 55, Raca.Bellato, "Infiltrator", "Masculino", 0, 0, 0,
      0, 0, 0, 0, new Equipamento(100, "Ranged Peito", new Melhoria([Talica.Favor, Talica.Favor, Talica.Favor, Talica.Blank, Talica.Blank, Talica.Blank, Talica.Blank]), 42),
      new Equipamento(100, "Ranged Calça", new Melhoria([Talica.Favor, Talica.Favor, Talica.Favor, Talica.Blank, Talica.Blank, Talica.Blank, Talica.Blank]), 42),
      new Equipamento(100, "Ranged Luva", new Melhoria([Talica.Favor, Talica.Favor, Talica.Favor, Talica.Blank, Talica.Blank, Talica.Blank, Talica.Blank]), 42),
      new Equipamento(100, "Ranged Bota", new Melhoria([Talica.Favor, Talica.Favor, Talica.Favor, Talica.Blank, Talica.Blank, Talica.Blank, Talica.Blank]), 42),
      new Equipamento(100, "Ranged Elmo", new Melhoria([Talica.Favor, Talica.Favor, Talica.Favor, Talica.Blank, Talica.Blank, Talica.Blank, Talica.Blank]), 42),
      new Equipamento(100, "Ranged Escudo", new Melhoria([Talica.Favor, Talica.Favor, Talica.Favor, Talica.Blank, Talica.Blank, Talica.Blank, Talica.Blank]), -1),
      new Equipamento(100, "Pistolinha de Emo", new Melhoria([Talica.Keen, Talica.Keen, Talica.Keen, Talica.Keen, Talica.Keen, Talica.Blank, Talica.Blank]), 217),
      new Equipamento(0, "", new Melhoria([Talica.Blank, Talica.Blank, Talica.Blank, Talica.Blank, Talica.Blank, Talica.Blank, Talica.Blank])),
      [new Equipamento(100, "Anel Fudido", null, 80),
        new Equipamento(100, "Outro Anel Fudido", null, 80)],
      [new Equipamento(100, "Amuleto Esmagador", null, 60),
        new Equipamento(100, "Bueno", null, 60)]);
    p.tempoJogado = 20;
    this.personagens = [p];
    // this.personagemService.getPersonagens(this.usuarioService.getUsuarioLogado().nome)
    // this.personagemService.getPersonagens('')
    //   .pipe(takeWhile(() => this.alive))
    //   .subscribe(personagens => {
    //     this.personagens = personagens;
    //   });
  }

  ngOnDestroy(): void {
    this.alive = false;
  }

  openModal(p: Personagem) {
    this.personagemDetalhe = p;
    $('#modalDetalhePersonagem').modal();
  }

  formataTempo(tempo) {
    const tempoInt = parseInt(tempo, 10) * 60;
    return tempoInt.tempoFormatado();
  }

}
