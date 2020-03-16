import {Component, HostListener, Input, OnInit} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {PersonagemService} from '../../@core/service/personagem.service';
import {Personagem} from '../../@core/model/personagem.model';
import {takeWhile} from 'rxjs/operators';
import {Inventario} from '../../@core/model/inventario.model';

@Component({
  selector: 'app-buscar-equipamentos',
  templateUrl: './buscar-equipamentos.component.html',
  styleUrls: ['./buscar-equipamentos.component.scss']
})
export class BuscarEquipamentosComponent implements OnInit {

  private personagem: Personagem;
  private nome: string;
  private alive = true;
  @Input() equipamento = true;
  private inventario: Inventario;

  constructor(private toaster: ToastrService, private personagemService: PersonagemService) {}

  ngOnInit() {}

  submit(nome) {
    if (nome) {
      if (this.equipamento) {
        this.personagemService.getEquipamentos(nome)
          .pipe(takeWhile(() => this.alive))
          .subscribe(per => {
            this.personagem = per;
          });
      } else {
        this.personagemService.getInventario(nome)
          .pipe(takeWhile(() => this.alive))
          .subscribe(inventario => {
            console.log(inventario);
            console.log(this.personagem);
            this.inventario = inventario;
          });
      }
    }
  }

  // @HostListener('window:keyup', ['$event'])
  // keyListener(event: KeyboardEvent) {
  //   if (event.key === 'Enter' && this.noFoco) {
  //     this.submit();
  //   }
  // }
}
