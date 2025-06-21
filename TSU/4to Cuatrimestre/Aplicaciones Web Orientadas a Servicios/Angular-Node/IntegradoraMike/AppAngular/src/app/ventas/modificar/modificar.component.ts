import { Component, OnInit } from '@angular/core';
import { VentasService, Ventas } from '../../services-ventas/ventas.service';
import {Router, ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-modificar',
  templateUrl: './modificar.component.html',
  styleUrls: ['./modificar.component.css']
})
export class ModificarVentas implements OnInit {

  ventas: Ventas = {
    id_venta: '',
    codigoVenta: '',
    productosVendidos: 0,
    total: '',
    nomCajero: ''
  };

  constructor(private VentasService:VentasService, private router:Router, private activeRoute:ActivatedRoute) { }

  ngOnInit(): void {
    const id_entrada = <string>this.activeRoute.snapshot.params['id'];
    console.log('id de entrada: '+id_entrada);

    if(id_entrada){
      this.VentasService.getUnaVenta(id_entrada).subscribe(res=>{
          this.ventas = res[0];
          console.log(res[0]);
        },
        err=>console.log(err)
      );
    }
  }

  modificar(){
    this.VentasService.editVenta(this.ventas.id_venta!, this.ventas).subscribe(res=>{
        console.log(res);
      },
        err=>console.log(err)
    );
    this.router.navigate(['/ventas']);
  }
}
