import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductDisplayComponent } from './product-display/product-display.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports:      [ CommonModule, NgbModule ],
  declarations: [ ProductDisplayComponent],
  exports:      [ ProductDisplayComponent ]
})
export class SharedModule { }
