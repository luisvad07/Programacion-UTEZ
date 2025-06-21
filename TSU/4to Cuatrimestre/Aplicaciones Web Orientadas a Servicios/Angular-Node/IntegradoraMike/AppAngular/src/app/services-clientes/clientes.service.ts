import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClientesService {

  url = '/api/clientes';

  constructor(private http: HttpClient) { }

  //get Clientes
  public getClientes(){
    return this.http.get(this.url);
  }

  //get un Cliente
  public getUnCliente(id:string){
    return this.http.get<Clientes[]>(this.url+'/'+id);
  }

  //agregar Cliente
  public addCliente(cliente:Clientes) {
    return this.http.post(this.url, cliente);
  }


  //eliminar Cliente
  public deleteCliente(id:string){
    return this.http.delete(this.url+'/'+id);
  }

  //modificar usuario
  public editCliente(id:string, cliente:Clientes){
    return this.http.put(this.url+'/'+id, cliente);
  }
}

export interface Clientes{
  id?:string;
  nombre?:string;
  apellidos?: string;
  email?:string;
  password?: string;
  id_cliente?: number;
  tipo_cliente?: string;
}
