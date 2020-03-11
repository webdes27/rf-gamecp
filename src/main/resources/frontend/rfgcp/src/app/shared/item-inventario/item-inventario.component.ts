import {Component, Input, OnInit} from '@angular/core';
import {Equipamento} from "../../@core/model/personagem.model";

@Component({
  selector: 'app-item-inventario',
  templateUrl: './item-inventario.component.html',
  styleUrls: ['./item-inventario.component.scss']
})
export class ItemInventarioComponent implements OnInit {

  @Input() equipamento: Equipamento;

  constructor() { }

  ngOnInit() {
  }

}
