import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import {SharedModule} from '../../shared/shared.module';
import { ImageSliderComponent } from './image-slider/image-slider.component';
import {NgbCarouselModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [HomeComponent, ImageSliderComponent],
    imports: [
        CommonModule,
        SharedModule,
        NgbCarouselModule
    ]
})
export class HomeModule { }
