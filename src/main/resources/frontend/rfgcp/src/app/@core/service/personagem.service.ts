import {Injectable} from '@angular/core';
import {AbstractService} from './abstract.service';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Personagem} from '../model/personagem.model';
import {Inventario} from '../model/inventario.model';
import {Banido} from "../model/banido.model";

@Injectable({
  providedIn: 'root'
})
export class PersonagemService extends AbstractService {

  private BASE_URL = this.API_REST + '/personagem/';

  constructor(http: HttpClient) {
    super(http);
  }

  getPersonagens(nomeUsuario: string): Observable<Personagem[]> {
    return this.http.get<Personagem[]>(this.BASE_URL + nomeUsuario);
  }

  getEquipamentos(nome: string): Observable<Personagem> {
    return this.http.get<Personagem>(this.BASE_URL + 'equipamento/' + nome);
  }

  getInventario(nome: string): Observable<Inventario> {
    return this.http.get<Inventario>(this.BASE_URL + 'inventario/' + nome);
  }

  getBanidos(nome = ""): Observable<Banido[]> {
    return this.http.get<Banido[]>(this.BASE_URL + 'lista-banidos/' + nome);
  }
}
