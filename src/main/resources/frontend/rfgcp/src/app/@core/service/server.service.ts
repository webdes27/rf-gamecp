import {Injectable} from "@angular/core";
import {AbstractService} from "./abstract.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ServerStats} from "../model/server-stats.model";
import {TopOnline} from "../model/top-online.model";

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
}
