import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ProductService} from '../../services/product.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  productDisplayTypes = ['featured product', 'Recently Added', 'Most Selling'];

  constructor(private productService: ProductService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  featuredProducts = [];
  recentlyAdded = [];
  mostSelling = [];

  ngOnInit() {
    this.getProducts();
  }


  getProducts() {
    this.productService.getRandomPage()
      .subscribe(data => {
        if (data != null){
          this.featuredProducts = data.data;
        }
      });

    this.productService.getMostSelling()
      .subscribe(data => {
        console.log(data);
        if (data != null) {
          this.recentlyAdded = data.data;
        }
      });

    this.productService.getRecentlyAdded()
      .subscribe(data => {
        console.log(data);
        if (data != null){
          this.mostSelling = data.data;
        }
      });
  }
}


