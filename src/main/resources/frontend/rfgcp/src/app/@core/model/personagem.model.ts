export enum Raca {
  Accretia= 'accretia',
  Bellato = 'bellato',
  Cora = 'cora'
}

export enum Talica {
  Keen = "00",
  Destruction = "01",
  Darkness = "02",
  Chaos = "03",
  Favor = "05",
  Wisdom = "06",
  SacredFire = "07",
  Belief = "08",
  Guard = "09",
  Glory = "10",
  Grace = "11",
  Mercy = "12",
  Blank = "15"
}

export class Melhoria {
  talica: Talica[];

  constructor(talica?: Talica[]) {
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
