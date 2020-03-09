import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import {BrowserModule} from '@angular/platform-browser';
import {ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToastrModule} from 'ngx-toastr';
import {RouterModule} from '@angular/router';
import {AppRoutingModule} from '../app-routing.module';
import { RegistrarComponent } from './registrar/registrar.component';
import { RecuperarFireguardComponent } from './recuperar-fireguard/recuperar-fireguard.component';
import { IndexComponent } from './index.component';



@NgModule({
  declarations: [LoginComponent, RegistrarComponent, RecuperarFireguardComponent, IndexComponent],
  imports: [
    CommonModule,
    BrowserModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    AppRoutingModule
  ]
})
export class IndexModule { }
