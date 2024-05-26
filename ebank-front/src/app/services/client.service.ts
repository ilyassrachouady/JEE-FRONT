import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Client} from "../model/client.model";

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(private  http:HttpClient) { }


  public getClients() : Observable<Array<Client>>{
    return this.http.get<Array<Client>>("http://localhost:8085/clients")
  }

  public searchClients(keyword : string) : Observable<Array<Client>>{
    return this.http.get<Array<Client>>("http://localhost:8085/clients/search?keyword="+keyword)
  }

  public saveClient(client : Client): Observable<Client>{
    return this.http.post<Client>("http://localhost:8085/clients", client)
  }

  public deleteClient(id : number){
    return this.http.delete("http://localhost:8085/clients/" + id)
  }
}
