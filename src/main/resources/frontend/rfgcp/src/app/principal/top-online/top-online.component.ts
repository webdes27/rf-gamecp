import {Component, OnDestroy, OnInit} from '@angular/core';
import {TopOnline} from "../../@core/model/top-online.model";
import {ServerService} from "../../@core/service/server.service";
import {takeWhile} from "rxjs/operators";

@Component({
  selector: 'app-top-online',
  templateUrl: './top-online.component.html',
  styleUrls: ['./top-online.component.scss']
})
export class TopOnlineComponent implements OnInit, OnDestroy {

  private topOnline: TopOnline[];
  private alive = true;

  constructor(private serverService: ServerService) {
    this.topOnline = [];
  }

  ngOnInit() {
    this.serverService.getTopOnlines()
      .pipe(takeWhile(() => this.alive))
      .subscribe(top => this.topOnline = top);
  }

  ngOnDestroy(): void {
  }

  roundValue(ptContribuicao: number) {
    return Math.round(ptContribuicao);
  }
}
