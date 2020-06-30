import { Customer } from '../../model/Customer';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { ApiService } from './../../service/api.service';
import { FormGroup, FormBuilder, Validators } from "@angular/forms";


@Component({
  selector: 'app-employee-edit',
  templateUrl: './customer-edit.component.html',
  styleUrls: ['./customer-edit.component.css']
})

export class CustomerEditComponent implements OnInit {
  submitted = false;
  editForm: FormGroup;
  customerData: Customer[];
  CustomerProfile: any = ['Potential Customer', 'New Customer', 'Impulsive Customer', 'Discount Customer', 'Loyal Customer']

  constructor(
    public fb: FormBuilder,
    private actRoute: ActivatedRoute,
    private apiService: ApiService,
    private router: Router
  ) {}

  ngOnInit() {
    this.updateCustomer();
    let id = this.actRoute.snapshot.paramMap.get('id');
    this.getCustomer(id);
    this.editForm = this.fb.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$')]],
      customerType: ['', [Validators.required]],
      phoneNumber: ['', [Validators.required, Validators.pattern('^[0-9]+$')]]
    })
  }

  // Choose options with select-dropdown
  updateProfile(e) {
    this.editForm.get('customerType').setValue(e, {
      onlySelf: true
    })
  }

  // Getter to access form control
  get myForm() {
    return this.editForm.controls;
  }

  getCustomer(id) {
    this.apiService.getCustomer(id).subscribe(data => {
      this.editForm.setValue({
        name: data['name'],
        email: data['email'],
        customerType: data['customerType'],
        phoneNumber: data['phoneNumber'],
      });
    });
  }

  updateCustomer() {
    this.editForm = this.fb.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$')]],
      customerType: ['', [Validators.required]],
      phoneNumber: ['', [Validators.required, Validators.pattern('^[0-9]+$')]]
    })
  }

  onSubmit() {
    this.submitted = true;
    if (!this.editForm.valid) {
      return false;
    } else {
      if (window.confirm('Are you sure?')) {
        let id = this.actRoute.snapshot.paramMap.get('id');
        this.apiService.updateCustomer(id, this.editForm.value)
          .subscribe(res => {
            this.router.navigateByUrl('/customer-list');
            console.log('Content updated successfully!')
          }, (error) => {
            console.log(error)
          })
      }
    }
  }

}
