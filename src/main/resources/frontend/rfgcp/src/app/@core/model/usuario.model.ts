export class Usuario {
  nome: string;
  senha: string;
  email: string;

  constructor(nome?: string, senha?: string, email?: string) {
    this.nome = nome;
    this.senha = senha;
    this.email = email;
  }
}
