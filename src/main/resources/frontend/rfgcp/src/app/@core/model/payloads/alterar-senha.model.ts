export class AlterarSenha {
  senhaAtual: string;
  novaSenha: string;
  nomeUsuario: string;

  constructor(senhaAtual?: string, novaSenha?: string, nomeUsuario?: string) {
    this.senhaAtual = senhaAtual;
    this.novaSenha = novaSenha;
    this.nomeUsuario = nomeUsuario;
  }
}
