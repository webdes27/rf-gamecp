import {Component, OnDestroy, OnInit} from '@angular/core';
import {ServerStats} from "../../@core/model/server-stats.model";
import {ServerService} from "../../@core/service/server.service";
import {takeWhile} from "rxjs/operators";

@Component({
  selector: 'app-informacoes',
  templateUrl: './informacoes.component.html',
  styleUrls: ['./informacoes.component.scss']
})
export class InformacoesComponent implements OnInit, OnDestroy {

  private stats: ServerStats;
  private alive = true;

  constructor(private serverService: ServerService) {

  }

  ngOnInit(): void {
    this.serverService.getEstatisticas()
      .pipe(takeWhile(() => this.alive))
      .subscribe(stats => this.stats = stats);
  }

  ngOnDestroy(): void {
    this.alive = false;
  }
}
