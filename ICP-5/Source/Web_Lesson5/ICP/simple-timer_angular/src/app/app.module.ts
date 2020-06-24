import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { SimpleTimer } from '../../projects/ng2-simple-timer/src/lib/ng2-simple-timer.service';

import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [SimpleTimer],
  bootstrap: [AppComponent]
})
export class AppModule { }
