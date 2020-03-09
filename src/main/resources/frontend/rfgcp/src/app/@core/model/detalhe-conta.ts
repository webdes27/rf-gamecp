export class DetalheConta {
  nome: string;
  email: string;
  cashPoint: string;
  ultimoLogon: Date;
  ultimoLogoff: Date;
  statusPersonagem: boolean;
  dataFinalPremium: Date;
  premium: boolean;

  constructor(nome?: string, email?: string, cashPoint?: string, ultimoLogon?: Date,
              ultimoLogoff?: Date, statusPersonagem?: boolean, dataFinalPremium?: Date,
              premium?: boolean) {
    this.nome = nome;
    this.email = email;
    this.cashPoint = cashPoint;
    this.ultimoLogon = ultimoLogon;
    this.ultimoLogoff = ultimoLogoff;
    this.statusPersonagem = statusPersonagem;
    this.dataFinalPremium = dataFinalPremium;
    this.premium = premium;
  }
}
