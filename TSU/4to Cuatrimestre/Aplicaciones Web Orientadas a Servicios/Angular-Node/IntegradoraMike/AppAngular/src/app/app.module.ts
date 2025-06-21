import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InicioComponent } from './componente/inicio/inicio.component';
import { AgregarComponent } from './componente/agregar/agregar.component';
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

@NgModule({
  declarations: [
    AppComponent,
    InicioComponent,
    AgregarComponent,
    ModificarComponent,
    AgregarProductos,
    InicioProductos,
    ModificarProductos,
    InicioCliente,
    AgregarCliente,
    ModificarCliente,
    InicioVentas,
    AgregarVentas,
    ModificarVentas
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
