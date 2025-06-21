import { Component, OnInit } from '@angular/core';
import { Productos, ProductosService } from 'src/app/services-productos/productos.service';
import { Router } from "@angular/router";

@Component({
  selector: 'app-agregar',
  templateUrl: './agregar.component.html',
  styleUrls: ['./agregar.component.css']
})
export class AgregarProductos implements OnInit {
  producto: Productos = {
    id:'',
    name_product:'',
    codigo_product: '',
    precio: 0,
    cantidad: 0,
    id_tipo_product: 0
  };

  constructor(private ProductosService:ProductosService, private router:Router){}

  ngOnInit(): void {
  }

  agregar(){
    delete this.producto.id;

    this.ProductosService.addProducto(this.producto).subscribe();
    this.router.navigate(['/productos']);
  }
}
