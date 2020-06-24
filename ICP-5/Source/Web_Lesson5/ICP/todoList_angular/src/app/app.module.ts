import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { TodoAppComponent } from './todo-app/todo-app.component';
import { AfterViewFocusDirective } from './todo-app/after-view-focus.directive';

@NgModule({
  declarations: [
    AppComponent,
    TodoAppComponent,
    AfterViewFocusDirective
  ],
  imports: [
    BrowserModule,
    CommonModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
