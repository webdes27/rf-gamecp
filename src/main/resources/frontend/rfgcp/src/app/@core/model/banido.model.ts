export class Banido {
  nomePersonagem: string;
  dataInicio: Date;
  dataTermino: Date;
  nomeUsuario: string;
  GM: string;
  razao: string;


  constructor(nomePersonagem?: string, dataInicio?: Date, dataTermino?: Date,
              nomeUsuario?: string, GM?: string, razao?: string) {
    this.nomePersonagem = nomePersonagem;
    this.dataInicio = dataInicio;
    this.dataTermino = dataTermino;
    this.nomeUsuario = nomeUsuario;
    this.GM = GM;
    this.razao = razao;
  }
}
