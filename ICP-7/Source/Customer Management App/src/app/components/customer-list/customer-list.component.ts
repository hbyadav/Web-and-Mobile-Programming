import { Component, OnInit } from '@angular/core';
import { ApiService } from './../../service/api.service';

@Component({
  selector: 'app-employee-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.css']
})

export class CustomerListComponent implements OnInit {

  Customer:any = [];

  constructor(private apiService: ApiService) {
    this.readCustomer();
  }

  ngOnInit() {}

  readCustomer(){
    this.apiService.getCustomers().subscribe((data) => {
     this.Customer = data;
    })
  }

  removeCustomer(customer, index) {
    if(window.confirm('Are you sure?')) {
        this.apiService.deleteCustomer(customer._id).subscribe((data) => {
          this.Customer.splice(index, 1);
        }
      )
    }
  }

}
