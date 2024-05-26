import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Client} from "../model/client.model";
import {ClientsComponent} from "../clients/clients.component";

@Component({
  selector: 'app-client-accounts',
  templateUrl: './client-accounts.component.html',
  styleUrl: './client-accounts.component.css'
})
export class ClientAccountsComponent implements OnInit{
  clientId!: string
  client!: Client
  constructor(private route: ActivatedRoute, private router: Router) {
    this.client = this.router.getCurrentNavigation()?.extras.state as Client
  }

  ngOnInit() {
    this.clientId = this.route.snapshot.params['id']

  }
}
