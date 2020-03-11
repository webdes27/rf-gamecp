import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AsideMenuComponent} from './aside-menu/aside-menu.component';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppRoutingModule} from '../app-routing.module';
import {FormsModule} from '@angular/forms';
import { ItemInventarioComponent } from './item-inventario/item-inventario.component';

@NgModule({
  declarations: [AsideMenuComponent, ItemInventarioComponent],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule
  ],
  exports: [
    CommonModule,
    FormsModule,
    BrowserModule,
    BrowserAnimationsModule,
    AsideMenuComponent
  ]
})
export class SharedModule {
}
