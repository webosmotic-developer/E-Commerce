import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {SearchCriteria} from '../../models/SearchCriteria';
import {ProductService} from '../../services/product.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  searchCriteria: SearchCriteria = new SearchCriteria();
  products = [];
  constructor(private productService: ProductService,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit(): void {
    console.log('String');
    if (this.route.snapshot.queryParamMap.has('s')) {
      this.searchCriteria.all = this.route.snapshot.queryParamMap.get('s');
      console.log('String' + this.searchCriteria.all);
      this.getProducts();
    }
  }
  getProducts() {
    this.productService.getSearchProducts(this.searchCriteria)
      .subscribe(data => {
        if (data != null) {
          this.products = data.data;
        }
      });
  }

  get(search: SearchCriteria) {
    this.searchCriteria = search;
    this.getProducts();
  }
}
