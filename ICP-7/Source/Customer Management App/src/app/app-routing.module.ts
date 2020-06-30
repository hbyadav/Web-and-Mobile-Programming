import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CustomerCreateComponent } from './components/customer-create/customer-create.component';
import { CustomerListComponent } from './components/customer-list/customer-list.component';
import { CustomerEditComponent } from './components/customer-edit/customer-edit.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'create-customer' },
  { path: 'create-customer', component: CustomerCreateComponent },
  { path: 'edit-customer/:id', component: CustomerEditComponent },
  { path: 'customer-list', component: CustomerListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
