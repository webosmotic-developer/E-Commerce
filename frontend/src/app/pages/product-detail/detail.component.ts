import {Component, OnInit} from '@angular/core';
import {ProductService} from '../../services/product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {CartService} from '../../services/cart.service';
import {CookieService} from 'ngx-cookie-service';
import {NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {

  title: string;
  count: number;
  productInfo: any = {};
  finalPrice: number = 0;
  deliveredDate: any;
  relatedProducts: any = [];

  constructor(
    config: NgbRatingConfig,
    private productService: ProductService,
    private cartService: CartService,
    private cookieService: CookieService,
    private route: ActivatedRoute,
    private router: Router,
  ) {
    config.max = 5;
    config.readonly = false;
    const id  = this.route.snapshot.paramMap.get('id')
    this.getProductDetail(id);
    this.getMaostSelling();
  }

  currentRate = 3.5;

  ngOnInit() {
    this.title = 'Product Detail';
    this.count = 1;
  }

  getProductDetail(id): void {
    if (id != null) {
      this.productService.getDetail(id).subscribe(
        prod => {
          console.log("prod========", prod);
          this.productInfo = prod.data;
          this.calculateTheFinalPrice(prod.data.unitPrice, prod.data.discount);
          console.log("prod========", this.productInfo);
          this.calculateDeliveryDate();
        },
        _ => console.log('Get Cart Failed')
      );
    }
  }

  getRelatedProduct(id){
    if (id != null) {
      this.productService.getRealtedProducts(id).subscribe(
        prod => {
          console.log("prod========", prod);
          this.relatedProducts = prod.data;
        },
        _ => console.log('Get Cart Failed')
      );
    }
  }

  getMaostSelling() {
    this.productService.getMostSelling()
      .subscribe(data => {
        console.log(data);
        if (data != null) {
          this.relatedProducts = data.data;
        }
      });
  }


  calculateTheFinalPrice (price, discount) {
    const s = 100 - discount;
    this.finalPrice = (price * (s / 100));
  }

  calculateDeliveryDate() {
    const date = new Date();
    const dayOfMonth = date.getDate() + 2;
    this.deliveredDate =  moment(date.setDate(dayOfMonth)).format("DD MMM, dddd ");
  }






  /*addToCart() {
    this.cartService
        .addItem(new ProductInOrder(this.productInfo, this.count))
        .subscribe(
            res => {
              if (!res) {
                console.log('Add Cart failed' + res);
                throw new Error();
              }
              this.router.navigateByUrl('/cart');
            },
            _ => console.log('Add Cart Failed')
        );
  }

  validateCount() {
    console.log('Validate');
    const max = this.productInfo.productStock;
    if (this.count > max) {
      this.count = max;
    } else if (this.count < 1) {
      this.count = 1;
    }
  }*/
}
