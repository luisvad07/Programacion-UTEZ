import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavigationComponent } from './shared/navigation/navigation.component';
import { LayoutModule } from '@angular/cdk/layout';
import { materialModules } from './types/material-modules';
import { AppRouterModule } from './shared/routers/app-router.module';
import { AuthModule } from './modules/auth/auth.module';
import { PersonalModule } from './modules/personal/personal.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CustomHttInterceptorService } from './services/http-interceptor.service';

@NgModule({
  declarations: [AppComponent, NavigationComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    LayoutModule,
    ...materialModules,
    AppRouterModule,
    AuthModule,
    PersonalModule,
    HttpClientModule
  ],
  exports: [AppComponent, NavigationComponent],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CustomHttInterceptorService,
      multi: true,
    }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
