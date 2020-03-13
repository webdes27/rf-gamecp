export class TopOnline {
  rank: number;
  status: boolean;
  raca: string;
  nivel: number;
  nomePersonagem: string;
  classe: string;
  tempoJogo: number;
  ptContribuicao: number;
  guilda: string;


  constructor(rank?: number, status?: boolean, raca?: string, nivel?: number, nomePersonagem?: string,
              classe?: string, tempoJogo?: number, ptContribuicao?: number, guilda?: string) {
    this.rank = rank;
    this.status = status;
    this.raca = raca;
    this.nivel = nivel;
    this.nomePersonagem = nomePersonagem;
    this.classe = classe;
    this.tempoJogo = tempoJogo;
    this.ptContribuicao = ptContribuicao;
    this.guilda = guilda;
  }
}
