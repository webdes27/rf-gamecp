import {Component, OnDestroy, OnInit} from '@angular/core';
import {InfoService} from '../../@core/service/info-service.service';
import {DetalheConta} from '../../@core/model/detalhe-conta.model';
import {takeWhile} from 'rxjs/operators';
import {UsuarioService} from '../../@core/service/usuario.service';

@Component({
  selector: 'app-detalhes',
  templateUrl: './detalhes.component.html',
  styleUrls: ['./detalhes.component.scss']
})
export class DetalhesComponent implements OnInit, OnDestroy {

  private alive: boolean;
  private detalheConta: DetalheConta;
  private semPremium = 'Premium Inativo';

  constructor(private infoService: InfoService, private usuarioService: UsuarioService) {
    this.alive = true;
  }

  ngOnInit() {
    this.infoService.getDetalhesIniciais(this.usuarioService.getUsuarioLogado().nome)
      .pipe(takeWhile(() => this.alive))
      .subscribe(data => {
        this.detalheConta = data;
        this.detalheConta.email = this.usuarioService.getUsuarioLogado().email;
      });
  }

  formataData(data: Date) {
    const partes = data.toString().split('T');
    const tempo = partes[1].split('.')[0];
    const dataPartes = partes[0].split('-');
    const dataFormatada = dataPartes[2] + '/' + dataPartes[1] + '/' + dataPartes[0];
    return dataFormatada + ' ' + tempo;
  }

  ngOnDestroy(): void {
    this.alive = false;
  }
}
