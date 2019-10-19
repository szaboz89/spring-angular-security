import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {ProductsComponent} from './pages/products/products.component';
import {LoginComponent} from './component/login/login.component';
import {AuthenticatedGuard} from './service/auth/authenticated-guard.service';
import {AdminComponent} from './pages/admin/admin.component';
import {AdminGuard} from './service/auth/admin-guard.service';

const routes: Routes = [
  {
    path: '', redirectTo: 'home', pathMatch: 'full'
  },
  {
    path: 'home', component: HomeComponent, pathMatch: 'full',
    canActivate: [AuthenticatedGuard]
  },
  {
    path: 'products', component: ProductsComponent, pathMatch: 'full',
    canActivate: [AuthenticatedGuard]
  },
  {
    path: 'admin', component: AdminComponent, pathMatch: 'full',
    canActivate: [AdminGuard]
  },
  {
    path: 'login', component: LoginComponent, pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
