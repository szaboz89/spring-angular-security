import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './component/header/header.component';
import {HomeComponent} from './pages/home/home.component';
import {ProductsComponent} from './pages/products/products.component';
import {HttpClientModule} from '@angular/common/http';
import {ProductService} from './service/product.service';
import {LoginComponent} from './component/login/login.component';
import {FormsModule} from '@angular/forms';
import {AuthService} from './service/auth/auth.service';
import {AuthenticatedGuard} from './service/auth/authenticated-guard.service';
import {AdminComponent} from './pages/admin/admin.component';
import {AdminGuard} from './service/auth/admin-guard.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ProductsComponent,
    AdminComponent,
    LoginComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    ProductService,
    AuthService,
    AuthenticatedGuard,
    AdminGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
