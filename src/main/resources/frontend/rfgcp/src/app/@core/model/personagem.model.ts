export enum Raca {
  Accretia,
  Bellato,
  Cora
}

export class Melhoria {
  qtd: number;
  talica: string;

  constructor(qtd?: number, talica?: string) {
    this.qtd = qtd;
    this.talica = talica;
  }
}

export class Equipamento {
  codigo: number;
  nome: string;
  melhoria: Melhoria;
  imgId: number;


  constructor(codigo?: number, nome?: string, melhoria?: Melhoria, imgId?: number) {
    this.codigo = codigo;
    this.nome = nome;
    this.melhoria = melhoria;
    this.imgId = imgId;
  }
}

export class Personagem {
  nome: string;
  nivel: number;
  raca: Raca;
  classe: string;
  tempoJogado: number;
  genero: string;
  dinheiro: number;
  ouro: number;
  ptContribuicao: number;
  ptCertos: number;
  ptOuro: number;
  ptCaca: number;
  ptProcessamento: number;
  peito: Equipamento;
  calca: Equipamento;
  luva: Equipamento;
  bota: Equipamento;
  elmo: Equipamento;
  escudo: Equipamento;
  arma: Equipamento;
  capa: Equipamento;
  aneis: Equipamento[];
  amuletos: Equipamento[];

  constructor(nome?: string, nivel?: number, raca?: Raca, classe?: string, genero?: string, ptContribuicao?: number,
              ptCertos?: number, ptOuro?: number, ptCaca?: number, ptProcessamento?: number, dinheiro?: number, ouro?: number,
              peito?: Equipamento, calca?: Equipamento, luva?: Equipamento, bota?: Equipamento,
              elmo?: Equipamento, escudo?: Equipamento, arma?: Equipamento, capa?: Equipamento,
              aneis?: Equipamento[], amuletos?: Equipamento[]) {
    this.nome = nome;
    this.nivel = nivel;
    this.raca = raca;
    this.ptContribuicao = ptContribuicao;
    this.ptCertos = ptCertos;
    this.ptOuro = ptOuro;
    this.ptCaca = ptCaca;
    this.ptProcessamento = ptProcessamento;
    this.peito = peito;
    this.calca = calca;
    this.luva = luva;
    this.bota = bota;
    this.elmo = elmo;
    this.escudo = escudo;
    this.arma = arma;
    this.capa = capa;
    this.aneis = aneis;
    this.amuletos = amuletos;
    this.classe = classe;
    this.genero = genero;
    this.dinheiro = dinheiro;
    this.ouro = ouro;
  }
}
