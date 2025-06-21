import { Component, OnInit } from '@angular/core';
import { VentasService, Ventas } from '../../services-ventas/ventas.service';
import { Productos} from 'src/app/services-productos/productos.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-agregar',
  templateUrl: './agregar.component.html',
  styleUrls: ['./agregar.component.css']
})
export class AgregarVentas implements OnInit{


  ventas: Ventas = {
    id_venta: '',
    id_producto: 0,
    name_product: '',
    codigoVenta: '',
    productosVendidos: 0,
    total: '',
    nomCajero: ''
  };

  constructor(private ventasService:VentasService, private router:Router){}

  ngOnInit(): void {

  }

  agregar(): void{
    delete this.ventas.id_venta;

    this.ventasService.addVenta(this.ventas).subscribe();
    this.router.navigate(['/ventas']);
  }

}
