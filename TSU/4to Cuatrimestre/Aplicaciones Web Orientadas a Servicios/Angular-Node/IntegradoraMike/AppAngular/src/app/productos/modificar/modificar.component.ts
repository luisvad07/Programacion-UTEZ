import { Component, OnInit } from '@angular/core';
import { Productos, ProductosService } from 'src/app/services-productos/productos.service';

import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-modificar',
  templateUrl: './modificar.component.html',
  styleUrls: ['./modificar.component.css']
})
export class ModificarProductos implements OnInit {
  producto: Productos = {
    id:'',
    name_product:'',
    codigo_product: '',
    precio: 0,
    cantidad: 0,
    id_tipo_product: 0
  };

  constructor(private ProductosService:ProductosService, private router:Router, private activeRoute:ActivatedRoute) { }

  ngOnInit(): void {
    const id_entrada = <string>this.activeRoute.snapshot.params['id'];
    console.log('id de entrada: '+id_entrada);

    if(id_entrada){
      this.ProductosService.getUnProducto(id_entrada).subscribe(res=>{
          this.producto = res[0];
          console.log(res[0]);
        },
        err=>console.log(err)
      );
    }
  }

  modificar(){
    this.ProductosService.editProducto(this.producto.id!, this.producto).subscribe(res=>{
        console.log(res);
      },
        err=>console.log(err)
    );
    this.router.navigate(['/productos']);
  }
}
