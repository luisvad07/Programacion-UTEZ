import { Component, OnInit } from '@angular/core';
import { ProductosService, Productos } from '../../services-productos/productos.service';
import { Router } from '@angular/router';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioProductos implements OnInit {
  //variable
  ListarProductos: Productos[] = [];
  title = 'appBootstrap';

  closeResult: string = '';

  letrero: String = '¿Estas seguro de Eliminar este registro?'

  constructor(private ProductosService: ProductosService, private router:Router, private modalService: NgbModal) {}

  ngOnInit(): void {
    this.listarProductos();
  }

  open(content:any) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }

  listarProductos() {
    this.ProductosService.getProductos().subscribe(res=>{
        console.log(res);
        this.ListarProductos=<any>res;
      }
    );
  }

  eliminar(id:string) {
    this.ProductosService.deleteProducto(id).subscribe(()=>{
        console.log('Producto Eliminado');
        this.listarProductos();
      },
      err=> console.log(err)
      );
  }

  modificar(id:string){
    this.router.navigate(['/editp/'+id]);
  }
}
