import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Inventario} from '../../@core/model/inventario.model';

@Component({
  selector: 'app-inventario',
  templateUrl: './inventario.component.html',
  styleUrls: ['./inventario.component.scss']
})
export class InventarioComponent implements OnInit {

  @Input() inventario: Inventario;

  constructor() {
  }

  ngOnInit() {
  }

}
