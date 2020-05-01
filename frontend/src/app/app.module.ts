import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {NavigationComponent} from './parts/navigation/navigation.component';
import {CardComponent} from './pages/card/card.component';
import {PaginationComponent} from './parts/pagination/pagination.component';
import {AppRoutingModule} from './app-routing.module';
import {LoginComponent} from './pages/login/login.component';
import {SignupComponent} from './pages/signup/signup.component';
import {DetailComponent} from './pages/product-detail/detail.component';
import {FormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {CartComponent} from './pages/cart/cart.component';
import {CookieService} from 'ngx-cookie-service';
import {ErrorInterceptor} from './_interceptors/error-interceptor.service';
import {JwtInterceptor} from './_interceptors/jwt-interceptor.service';
import {OrderComponent} from './pages/order/order.component';
import {OrderDetailComponent} from './pages/order-detail/order-detail.component';
import {ProductListComponent} from './pages/product-list/product.list.component';
import {UserDetailComponent} from './pages/user-edit/user-detail.component';
import {ProductEditComponent} from './pages/product-edit/product-edit.component';
import {FooterComponent} from './parts/footer/footer.component';
import {HomeModule} from './parts/home/home.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { VerifyUserEmailComponent } from './pages/verify-user-email/verify-user-email.component';
import { SignUpSuccessMessageComponent } from './pages/sign-up-success-message/sign-up-success-message.component';
import {ProductsComponent} from './parts/products/products.component';
import {SharedModule} from './shared/shared.module';
import { FilterComponent } from './parts/products/filter/filter.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    CardComponent,
    PaginationComponent,
    LoginComponent,
    SignupComponent,
    DetailComponent,
    CartComponent,
    OrderComponent,
    OrderDetailComponent,
    ProductListComponent,
    UserDetailComponent,
    ProductEditComponent,
    FooterComponent,
    VerifyUserEmailComponent,
    SignUpSuccessMessageComponent,
    ProductsComponent,
    FilterComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        HomeModule,
        NgbModule,
        SharedModule,
    ],
  providers: [CookieService,
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
