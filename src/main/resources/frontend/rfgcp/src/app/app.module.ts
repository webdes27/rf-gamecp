import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {SharedModule} from './shared/shared.module';
import {AppRoutingModule} from './app-routing.module';
import {IndexModule} from './index/index.module';
import {PrincipalModule} from './principal/principal.module';
import {HTTP_INTERCEPTORS, HttpClientModule, HttpClientXsrfModule} from '@angular/common/http';
import {AuthGuard} from './@core/security/auth.guard';
import {ErrorInterceptor} from './@core/security/error.interceptor';
import {HttpXsrfInterceptor} from './@core/security/http-xsrf.interceptor';
import {AuthInterceptor} from './@core/security/auth.interceptor';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    AppRoutingModule,
    HttpClientModule,
    PrincipalModule,
    BrowserModule,
    SharedModule,
    IndexModule,
    HttpClientXsrfModule.withOptions(
      {cookieName: 'XSRF-TOKEN', headerName: 'X-XSRF-TOKEN'}
    )
  ],
  providers: [
    AuthGuard,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: HttpXsrfInterceptor, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
