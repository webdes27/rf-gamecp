interface Number {
  tempoFormatado(): string;
}

Number.prototype.tempoFormatado = function(): string {
  return formataSegundos(this);
};

function formataSegundos(seconds: number): string {

  const days = Math.floor(seconds / (3600 * 24));
  seconds -= days * 3600 * 24;
  const hours = Math.floor(seconds / 3600);
  seconds -= hours * 3600;
  const minutes = Math.floor(seconds / 60);
  seconds -= minutes * 60;

  let duracao = '';
  duracao += (days ? days + 'd ' : '');
  duracao += (hours ? hours + 'h ' : '');
  duracao += (minutes < 10 ? '0' + minutes + 'm ' : minutes + 'm ');
  return duracao;
}
