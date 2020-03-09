interface Item {
  slot: number;
  nome: string;
  quantidade: number;
  melhoria: number;
}

export class Inventario {
  itens: Item[];
  constructor(item?: Item[]) {
    this.itens = item;
  }
}
