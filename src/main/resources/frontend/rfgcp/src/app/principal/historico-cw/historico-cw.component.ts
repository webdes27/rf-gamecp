import {Component, OnDestroy, OnInit} from '@angular/core';
import {ServerService} from "../../@core/service/server.service";
import {takeWhile} from "rxjs/operators";
import {HistoricoCW} from "../../@core/model/historico-cw.model";

@Component({
  selector: 'app-historico-cw',
  templateUrl: './historico-cw.component.html',
  styleUrls: ['./historico-cw.component.scss']
})
export class HistoricoCwComponent implements OnInit, OnDestroy {

  private optionsWin: any;
  private optionsLoses: any;
  private alive = true;
  private historicos: any[];

  constructor(private serverService: ServerService) {
  }

  ngOnInit() {
    this.serverService.getGuerraChipStats()
      .pipe(takeWhile(() => this.alive))
      .subscribe(stats => {
        const racas = ['Accretia', 'Bellato', 'Cora'];
        const qtdsV = [stats.awins, stats.bwins, stats.cwins];
        const qtdsD = [stats.aloses, stats.bloses, stats.closes];
        this.optionsWin = this.buildGrafico('Total de Vitórias', racas, qtdsV);
        this.optionsLoses = this.buildGrafico('Total de Derrotas', racas, qtdsD);
      });
    this.serverService.getHistoricoCW()
      .pipe(takeWhile(() => this.alive))
      .subscribe(data => {
        this.historicos = Array.from(
          new Set(data.map(h =>
              new Date(h.dataInicio.split('T').join(',')).toLocaleDateString(navigator.language, {})
            )
          ))
          .map(d => {
            return {
              dia: d,
              linhas: data.filter(h => {
                const date = new Date(h.dataInicio.split('T').join(','));
                const comp = date.toLocaleDateString(navigator.language, {});
                return d === comp;
              })
                .map(linha => {
                  return {
                    horarioInicio: new Date(linha.dataInicio.split('T').join(',')),
                    horarioFim: new Date(linha.dataFim.split('T').join(',')),
                    racaVencedora: linha.racaVencedora,
                    racaPerdedora: linha.racaPerdedora,
                    tempoDecorrido: linha.tempoDecorrido,
                  }
                }),
            }
          })
      });
  }

  ngOnDestroy(): void {
    this.alive = false;
  }

  private buildGrafico(txt, racas, qtds) {
    return {
      title: {
        subtext: txt
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      toolbox: {
        show: true,
        feature: {
          saveAsImage: {
            title: 'Salvar como imagem'
          }
        }
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'value',
        boundaryGap: [0, 0.01]
      },
      yAxis: {
        type: 'category',
        data: racas
      },
      series: [
        {
          name: 'Quantidade',
          type: 'bar',
          data: qtds
        },
      ]
    };
  }

  formataData(data) {
    return new Date(data).toLocaleDateString(navigator.language, {});
  }

  formataHora(data) {
    return data.toLocaleTimeString(navigator.language, {
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  getTermino(tempoDecorrido: number, dataInicio: String) {
    const dtFinal = new Date(dataInicio.toString());
    dtFinal.setSeconds(tempoDecorrido);
    return this.formataHora(dtFinal);
  }
}
