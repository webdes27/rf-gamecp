import {NgModule} from '@angular/core';
import {PrincipalComponent} from './principal.component';
import {SharedModule} from '../shared/shared.module';
import {AppRoutingModule} from '../app-routing.module';
import {AlterarSenhaComponent} from './alterar-senha/alterar-senha.component';
import {PersonagensComponent} from './personagens/personagens.component';
import {BuscarEquipamentosComponent} from './buscar-equipamentos/buscar-equipamentos.component';
import {BuscarInventarioComponent} from './buscar-inventario/buscar-inventario.component';
import {ListaBanidosComponent} from './lista-banidos/lista-banidos.component';
import {ListaConcelhoComponent} from './lista-concelho/lista-concelho.component';
import {HistoricoCwComponent} from './historico-cw/historico-cw.component';
import {TopOnlineComponent} from './top-online/top-online.component';
import {InformacoesComponent} from './informacoes/informacoes.component';
import {ReactiveFormsModule} from '@angular/forms';
import {DetalhesComponent} from './detalhes/detalhes.component';
import {DetalhePersonagemComponent} from './detalhe-personagem/detalhe-personagem.component';
import {InventarioComponent} from './inventario/inventario.component';
import {NgxEchartsModule} from "ngx-echarts";

@NgModule({
  declarations: [
    PrincipalComponent,
    AlterarSenhaComponent,
    PersonagensComponent,
    BuscarEquipamentosComponent,
    BuscarInventarioComponent,
    ListaBanidosComponent,
    ListaConcelhoComponent,
    HistoricoCwComponent,
    TopOnlineComponent,
    InformacoesComponent,
    DetalhesComponent,
    DetalhePersonagemComponent,
    InventarioComponent
  ],
    imports: [
        SharedModule,
        AppRoutingModule,
        ReactiveFormsModule,
        NgxEchartsModule
    ]
})
export class PrincipalModule {
}
