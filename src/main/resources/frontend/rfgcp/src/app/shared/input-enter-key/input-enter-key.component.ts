import {Component, HostListener, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-input-enter-key',
  templateUrl: './input-enter-key.component.html',
  styleUrls: ['./input-enter-key.component.scss']
})
export class InputEnterKeyComponent implements OnInit {

  @Input() nome: string;
  private noFoco: boolean;
  @Input() submit: any;
  @Input() label: string = null;
  @Input() placeholder: string;

  constructor() { }

  ngOnInit() {
  }

  @HostListener('window:keyup', ['$event'])
  keyListener(event: KeyboardEvent) {
    if (event.key === 'Enter' && this.noFoco) {
      this.submit();
    }
  }

}
