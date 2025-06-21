import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductosService {

  url = '/api/productos';

  constructor(private http: HttpClient) { }

  //get productos
  public getProductos(){
    return this.http.get(this.url);
  }

  //get un producto
  public getUnProducto(id:string){
    return this.http.get<Productos[]>(this.url+'/'+id);
  }

  //agregar producto
  public addProducto(producto:Productos) {
    return this.http.post(this.url, producto);
  }


  //eliminar producto
  public deleteProducto(id:string){
    return this.http.delete(this.url+'/'+id);
  }

  //modificar producto
  public editProducto(id:string, producto:Productos){
    return this.http.put(this.url+'/'+id, producto);
  }
}

export interface Productos{
  id?:string;
  name_product?:string;
  codigo_product?: string;
  precio?: number;
  cantidad?: number;
  id_tipo_product?: number;
  rol?: string;
}
