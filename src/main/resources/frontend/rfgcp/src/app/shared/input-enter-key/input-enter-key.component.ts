import {Component, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-input-enter-key',
  templateUrl: './input-enter-key.component.html',
  styleUrls: ['./input-enter-key.component.scss']
})
export class InputEnterKeyComponent implements OnInit {

  private nome: string;
  private noFoco: boolean;
  @Output() submit = new EventEmitter();
  @Input() label: string = null;
  @Input() placeholder: string;

  constructor() { }

  ngOnInit() {
  }

  @HostListener('window:keyup', ['$event'])
  keyListener(event: KeyboardEvent) {
    if (event.key === 'Enter' && this.noFoco) {
      this.submit.emit(this.nome);
    }
  }

}
