export class AlterarSenha {
  senhaAtual: string;
  novaSenha: string;

  constructor(senhaAtual?: string, novaSenha?: string) {
    this.senhaAtual = senhaAtual;
    this.novaSenha = novaSenha;
  }
}
