import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ClientsComponent} from "./clients/clients.component";
import {AccountsComponent} from "./accounts/accounts.component";
import {NewClientComponent} from "./new-client/new-client.component";
import {ClientAccountsComponent} from "./client-accounts/client-accounts.component";

const routes: Routes = [
  {path : "clients", component: ClientsComponent},
  {path : "accounts", component: AccountsComponent},
  {path : "new-client", component : NewClientComponent},
  {path : "client-accounts/:id", component : ClientAccountsComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
