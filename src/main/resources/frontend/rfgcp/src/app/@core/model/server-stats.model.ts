export class ServerStats {
  acc: number;
  bell: number;
  cora: number;

  maxOn: number;
  avgOn: number;

  totalContas: number;
  totalPersonagens: number;
  personagensExistentes: number;
  personagensDeletados: number;


  constructor(acc?: number, bell?: number, cora?: number, maxOn?: number, avgOn?: number,
              totalContas?: number, totalPersonagens?: number, personagensExistentes?: number,
              personagensDeletados?: number) {
    this.acc = acc;
    this.bell = bell;
    this.cora = cora;
    this.maxOn = maxOn;
    this.avgOn = avgOn;
    this.totalContas = totalContas;
    this.totalPersonagens = totalPersonagens;
    this.personagensExistentes = personagensExistentes;
    this.personagensDeletados = personagensDeletados;
  }
}
