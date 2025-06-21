import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class UsuariosService {

  url = '/api/empleados';

  constructor(private http: HttpClient) { }

  //get usuarios
  public getUsuarios(){
    return this.http.get(this.url);
  }

  //get un usuario
  public getUnUsuario(id:string){
    return this.http.get<Usuario[]>(this.url+'/'+id);
  }

  //agregar usuario
  public addUsuario(usuario:Usuario) {
    return this.http.post(this.url, usuario);
  }


  //eliminar usuario
  public deleteUsuario(id:string){
    return this.http.delete(this.url+'/'+id);
  }

  //modificar usuario
  public editUsuario(id:string, usuario:Usuario){
    return this.http.put(this.url+'/'+id, usuario);
  }

}

export interface Usuario{
  id?:string;
  name?:string;
  surname?: string;
  lastname?: string;
  password?: string;
  curp?: string;
  id_usuario?: number;
  rol?: string;
}
