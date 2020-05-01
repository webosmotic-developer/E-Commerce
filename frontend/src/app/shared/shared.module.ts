import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductDisplayComponent } from './product-display/product-display.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { ProductsDisplayComponent } from './products-display/products-display.component';

@NgModule({
  imports:      [ CommonModule, NgbModule ],
  declarations: [ ProductDisplayComponent, ProductsDisplayComponent],
    exports: [ProductDisplayComponent, ProductsDisplayComponent]
})
export class SharedModule { }
