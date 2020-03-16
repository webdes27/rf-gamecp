import {Equipamento} from "./personagem.model";

export class Inventario {
  equipamentos: Equipamento[];
  constructor(equipamentos?: Equipamento[]) {
    this.equipamentos = equipamentos;
  }
}
