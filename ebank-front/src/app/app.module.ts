import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { AdminTemplateComponent } from './admin-template/admin-template.component';
import {MatToolbar, MatToolbarModule} from "@angular/material/toolbar";
import {MatButton} from "@angular/material/button";
import { ClientsComponent } from './clients/clients.component';
import { AccountsComponent } from './accounts/accounts.component';
import {HttpClientModule} from "@angular/common/http";
import {MatCard, MatCardModule} from "@angular/material/card";
import {MatDivider, MatDividerModule} from "@angular/material/divider";
import {MatTable, MatTableModule} from "@angular/material/table";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatError} from "@angular/material/form-field";
import {ReactiveFormsModule} from "@angular/forms";
import { NewClientComponent } from './new-client/new-client.component';
import { ClientAccountsComponent } from './client-accounts/client-accounts.component';

@NgModule({
  declarations: [
    AppComponent,
    AdminTemplateComponent,
    ClientsComponent,
    AccountsComponent,
    NewClientComponent,
    ClientAccountsComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        MatToolbarModule,
        MatButton,
        HttpClientModule,
        MatCardModule,
        MatDividerModule,
        MatTableModule,
        MatPaginatorModule,
        MatError,
        ReactiveFormsModule
    ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
