import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavigationComponent } from './shared/navigation/navigation.component';
import { AppRouterModule } from './shared/routers/app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppComponent } from './app.component';
import { ScreenComponent } from './shared/splash/screen/screen.component';
import { PersonalModule } from './modules/personal/personal.module';
import { AuthModule } from './modules/auth/auth.module';
import { materialModules } from './services/material.modules';


@NgModule({
  declarations: [ NavigationComponent, AppComponent, ScreenComponent ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRouterModule,
    BrowserAnimationsModule,
    ...materialModules,
    PersonalModule,
    AuthModule,
  ],
  providers: [
  ],
  exports: [ AppComponent, NavigationComponent, ScreenComponent ],
  bootstrap: [ AppComponent ],
})
export class AppModule {
}
