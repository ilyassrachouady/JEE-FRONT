import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {ClientService} from "../services/client.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {catchError, map, Observable, throwError} from "rxjs";
import {Client} from "../model/client.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrl: './clients.component.css'
})
export class ClientsComponent implements OnInit{
  public clients! : Observable<Array<Client>>
  public error! : string
  public searchFormGroup! : FormGroup

  @ViewChild(MatPaginator) paginator ! : MatPaginator
  constructor(private clientService : ClientService, private fb : FormBuilder, private router: Router) {
  }


  ngOnInit() {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control("")
    })
    this.handleSearchClient()
  }

  handleSearchClient() {
    let kw = this.searchFormGroup?.value.keyword
    this.clients = this.clientService.searchClients(kw).pipe(
      catchError(err => {
        this.error = err.message
        return throwError(() => new Error(err))
      })
    )

  }

  handleDeleteClient(c: Client) {
    let conf = confirm("Are you sure?")
    if (!conf) return
    this.clientService.deleteClient(c.id).subscribe({
      next:(resp) => {
        this.clients = this.clients.pipe(
          map(data => {
            let index = data.indexOf(c)
            data.slice(index, 1)
            return data
          })
        );
      },
      error: err => {
        console.log(err)
      }
    })
  }

  handleClientAccounts(client: Client) {
    this.router.navigateByUrl("client-accounts/"+client.id, {state: client})
  }
}
