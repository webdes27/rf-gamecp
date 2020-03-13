import {Injectable} from '@angular/core';
import {AbstractService} from './abstract.service';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DetalheConta} from '../model/detalhe-conta';

@Injectable({
  providedIn: 'root'
})
export class InfoService extends AbstractService {

  private BASE_URL = this.API_REST + '/info/';

  constructor(http: HttpClient) {
    super(http);
  }

  getDetalhesIniciais(nomeUsuario: string): Observable<DetalheConta> {
    return this.http.get<DetalheConta>(this.BASE_URL + 'conta/' + nomeUsuario);
  }
}
