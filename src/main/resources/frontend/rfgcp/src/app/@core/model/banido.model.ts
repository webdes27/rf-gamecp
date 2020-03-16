export class Banido {
  personagens: string;
  dataInicio: Date;
  periodo: number;
  nomeUsuario: string;
  gm: string;
  razao: string;


  constructor(personagens?: string, dataInicio?: Date, periodo?: number,
              nomeUsuario?: string, gm?: string, razao?: string) {
    this.personagens = personagens;
    this.dataInicio = dataInicio;
    this.periodo = periodo;
    this.nomeUsuario = nomeUsuario;
    this.gm = gm;
    this.razao = razao;
  }
}
