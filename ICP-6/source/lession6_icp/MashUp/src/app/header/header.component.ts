import { Component } from '@angular/core';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styles: [
    '.background {background:#bb1515; color: #0a0909}',
    'li a { color: white}',
    'ul.nav a:hover { color: #080809  }'
  ]
})
export class HeaderComponent {
  constructor() {}

  }
