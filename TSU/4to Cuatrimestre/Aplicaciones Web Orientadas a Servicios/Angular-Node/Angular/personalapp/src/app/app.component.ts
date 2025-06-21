import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'personalapp';
  counter: number = 0;
  adding(){
    this.counter += 1;
  }
  minus(){
    this.counter -=1;
  }
}
