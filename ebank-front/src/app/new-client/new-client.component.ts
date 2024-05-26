import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Client} from "../model/client.model";
import {ClientService} from "../services/client.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-client',
  templateUrl: './new-client.component.html',
  styleUrl: './new-client.component.css'
})
export class NewClientComponent implements OnInit{

  newClientFormGroup! : FormGroup

  constructor(private fb : FormBuilder, private clientService: ClientService, private router : Router) {
  }

  ngOnInit() {
    this.newClientFormGroup = this.fb.group({
      name : this.fb.control(null, [Validators.required, Validators.minLength(4)]),
      email : this.fb.control(null, [Validators.required ,Validators.email]),
    })
  }

  handleSavedClient() {
    let client: Client = this.newClientFormGroup.value
    this.clientService.saveClient(client).subscribe({
      next: data => {
        alert("Client has been successfully saved!")
        this.router.navigateByUrl("/clients")
      },
      error: err => {
        console.log(err)
      }
    })
  }
}
