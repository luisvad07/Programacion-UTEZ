import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AgregarComponent } from './componente/agregar/agregar.component';
import { InicioComponent } from './componente/inicio/inicio.component';
import { ModificarComponent } from './componente/modificar/modificar.component';
import { AgregarProductos } from './productos/agregar/agregar.component';
import { InicioProductos } from './productos/inicio/inicio.component';
import { ModificarProductos } from './productos/modificar/modificar.component';
import { InicioCliente } from './clientes/inicio/inicio.component';
import { AgregarCliente } from './clientes/agregar/agregar.component';
import { ModificarCliente } from './clientes/modificar/modificar.component';
import { InicioVentas } from './ventas/inicio/inicio.component';
import { AgregarVentas } from './ventas/agregar/agregar.component';
import { ModificarVentas } from './ventas/modificar/modificar.component';
import { InicioLogin } from './login/inicio/inicio.component';



const routes: Routes = [
  {path: '', redirectTo: '/inicio', pathMatch: 'full'},
  {path: 'inicio', component: InicioLogin},
  {path: 'empleados', component: InicioComponent},
  {path: 'add', component: AgregarComponent},
  {path: 'edit/:id', component: ModificarComponent},
  {path: 'productos', component: InicioProductos},
  {path: 'addp', component: AgregarProductos},
  {path: 'editp/:id', component: ModificarProductos},
  {path: 'clientes', component: InicioCliente},
  {path: 'addc', component: AgregarCliente},
  {path: 'editc/:id', component: ModificarCliente},
  {path: 'ventas', component: InicioVentas},
  {path: 'addv', component: AgregarVentas},
  {path: 'editv/:id', component: ModificarVentas}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
