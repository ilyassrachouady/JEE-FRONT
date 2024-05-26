import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ClientsComponent} from "./clients/clients.component";
import {AccountsComponent} from "./accounts/accounts.component";
import {NewClientComponent} from "./new-client/new-client.component";
import {ClientAccountsComponent} from "./client-accounts/client-accounts.component";
import {LoginComponent} from "./login/login.component";
import {AdminTempComponent} from "./admin-temp/admin-temp.component";
import {AuthenticationGuard} from "./guards/authentication.guard";
import {AuthorizationGuard} from "./guards/authorization.guard";
import {NotAuthorizedComponent} from "./not-authorized/not-authorized.component";

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "", redirectTo: "/login", pathMatch: "full"},
  {path: "admin", component: AdminTempComponent, canActivate: [AuthenticationGuard],
    children: [
      {path : "clients", component: ClientsComponent},
      {path : "accounts", component: AccountsComponent},
      {path : "new-client", component : NewClientComponent, canActivate: [AuthorizationGuard], data: {role: "ADMIN"}},
      {path : "client-accounts/:id", component : ClientAccountsComponent},
      {path: "notAuthorized", component: NotAuthorizedComponent}
    ]},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
