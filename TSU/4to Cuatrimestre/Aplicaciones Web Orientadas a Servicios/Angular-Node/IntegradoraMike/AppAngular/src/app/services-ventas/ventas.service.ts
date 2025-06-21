import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class VentasService {
  url = '/api/ventas';

  constructor(private http: HttpClient) { }

  //get Ventas
  public getVentas(){
    return this.http.get(this.url);
  }

  //get una venta
  public getUnaVenta(id:string){
    return this.http.get<Ventas[]>(this.url+'/'+id);
  }

  //agregar venta
  public addVenta(venta:Ventas) {
    return this.http.post(this.url, venta);
  }


  //eliminar venta
  public deleteVenta(id:string){
    return this.http.delete(this.url+'/'+id);
  }

  //modificar venta
  public editVenta(id:string, venta:Ventas){
    return this.http.put(this.url+'/'+id, venta);
  }
}

export interface Ventas{
  id_venta?:string;
  id_producto?:number;
  name_product?:string;
  codigoVenta?:string;
  productosVendidos?: number;
  total?: string;
  nomCajero?: string;
}

