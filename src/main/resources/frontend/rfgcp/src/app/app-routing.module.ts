import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PrincipalComponent} from './principal/principal.component';
import {BuscarEquipamentosComponent} from './principal/buscar-equipamentos/buscar-equipamentos.component';
import {BuscarInventarioComponent} from './principal/buscar-inventario/buscar-inventario.component';
import {HistoricoCwComponent} from './principal/historico-cw/historico-cw.component';
import {InformacoesComponent} from './principal/informacoes/informacoes.component';
import {AlterarSenhaComponent} from './principal/alterar-senha/alterar-senha.component';
import {ListaConcelhoComponent} from './principal/lista-concelho/lista-concelho.component';
import {ListaBanidosComponent} from './principal/lista-banidos/lista-banidos.component';
import {TopOnlineComponent} from './principal/top-online/top-online.component';
import {PersonagensComponent} from './principal/personagens/personagens.component';
import {IndexComponent} from './index/index.component';
import {LoginComponent} from './index/login/login.component';
import {RegistrarComponent} from './index/registrar/registrar.component';
import {RecuperarFireguardComponent} from './index/recuperar-fireguard/recuperar-fireguard.component';
import {DetalhesComponent} from './principal/detalhes/detalhes.component';
import {AuthGuard} from './@core/security/auth.guard';


const routes: Routes = [
  {path: '', redirectTo: 'index', pathMatch: 'full'},
  {
    path: 'index', component: IndexComponent,
    children: [
      {path: '', redirectTo: 'login', pathMatch: 'full'},
      {path: 'login', component: LoginComponent},
      {path: 'registrar', component: RegistrarComponent},
      {path: 'recuperar-fireguard', component: RecuperarFireguardComponent},
    ]
  },
  {
    path: 'principal', component: PrincipalComponent, canActivate: [AuthGuard],
    children: [
      {path: '', redirectTo: 'home', pathMatch: 'full'},
      {path: 'home', component: DetalhesComponent},
      {path: 'buscar-equipamento', component: BuscarEquipamentosComponent},
      {path: 'buscar-inventario', component: BuscarInventarioComponent},
      {path: 'historico-cw', component: HistoricoCwComponent},
      {path: 'informacoes', component: InformacoesComponent},
      {path: 'alterar-senha', component: AlterarSenhaComponent},
      {path: 'lista-concelho', component: ListaConcelhoComponent},
      {path: 'lista-banidos', component: ListaBanidosComponent},
      {path: 'top-online', component: TopOnlineComponent},
      {path: 'personagens', component: PersonagensComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
