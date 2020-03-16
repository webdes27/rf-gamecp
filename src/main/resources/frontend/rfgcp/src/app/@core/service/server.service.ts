import {Injectable} from "@angular/core";
import {AbstractService} from "./abstract.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ServerStats} from "../model/server-stats.model";
import {TopOnline} from "../model/top-online.model";
import {Banido} from "../model/banido.model";
import {GuerraChipStats, HistoricoCW} from "../model/historico-cw.model";
import {Concelhos} from "../model/concelhos.model";

@Injectable({
  providedIn: 'root'
})
export class ServerService extends AbstractService {

  private BASE_URL = this.API_REST + '/server/';

  constructor(http: HttpClient) {
    super(http);
  }

  getEstatisticas(): Observable<ServerStats> {
    return this.http.get<ServerStats>(this.BASE_URL + 'estats');
  }

  getTopOnlines(): Observable<TopOnline[]> {
    return this.http.get<TopOnline[]>(this.BASE_URL + 'top-online');
  }

  getBanidos(nome = ""): Observable<Banido[]> {
    return this.http.get<Banido[]>(this.BASE_URL + 'lista-banidos/' + nome);
  }

  getHistoricoCW(): Observable<HistoricoCW[]> {
    return this.http.get<HistoricoCW[]>(this.BASE_URL + 'historico-cw');
  }

  getGuerraChipStats(): Observable<GuerraChipStats> {
    return this.http.get<GuerraChipStats>(this.BASE_URL + 'estatisticas-cw');
  }

  getConcelhos(): Observable<Concelhos> {
    return this.http.get<Concelhos>(this.BASE_URL + 'concelhos');
  }
}
