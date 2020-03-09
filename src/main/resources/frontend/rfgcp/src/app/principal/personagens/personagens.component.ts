import {Component, OnDestroy, OnInit} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {UsuarioService} from '../../@core/service/usuario.service';
import {Personagem} from '../../@core/model/personagem.model';
import {takeWhile} from 'rxjs/operators';
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
    // this.personagemService.getPersonagens(this.usuarioService.getUsuarioLogado().nome)
    this.personagemService.getPersonagens('')
      .pipe(takeWhile(() => this.alive))
      .subscribe(personagens => {
        this.personagens = personagens;
      });
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
