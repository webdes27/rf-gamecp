import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../../environments/environment';

export class AbstractService {

  public headers: HttpHeaders;
  protected http: HttpClient;

  public readonly API_REST = environment.api_url;

  constructor(http: HttpClient) {
    this.http = http;
    this.headers = new HttpHeaders();
    this.headers.append('Content-Type', 'application/json');
  }

}
