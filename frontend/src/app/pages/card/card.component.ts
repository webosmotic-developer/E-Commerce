import {Component, OnDestroy, OnInit} from '@angular/core';
// import {prod, products} from '../shared/mockData';
import {ProductService} from '../../services/product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit, OnDestroy {


  title: string;
  page: any;
  private paramSub: Subscription;
  private querySub: Subscription;


  constructor(private productService: ProductService,
              private route: ActivatedRoute,
              private router: Router) {

  }


  ngOnInit() {
    /*  this.querySub = this.route.queryParams.subscribe(() => {
        this.update();
      });
      this.paramSub = this.route.params.subscribe(() => {
        this.update();
      });*/

  }

  ngOnDestroy(): void {
    this.querySub.unsubscribe();
    this.paramSub.unsubscribe();
  }

  /* update() {
     if (this.route.snapshot.queryParamMap.get('page')) {
       const currentPage = +this.route.snapshot.queryParamMap.get('page');
       const size = +this.route.snapshot.queryParamMap.get('size');
       this.getProds(currentPage, size);
     } else {
       this.getProds();
     }
   }*/
  /*getProds(page: number = 1, size: number = 3) {
    if (this.route.snapshot.url.length == 1) {
      this.productService.getAllInPage(+page, +size)
        .subscribe(data => {
          console.log(data);
          this.page = data;
          this.title = 'Get Whatever You Want!';
        });
    } else { //  /category/:id
      const type = this.route.snapshot.url[1].path;
      this.productService.getCategoryInPage(+type, page, size)
        .subscribe(categoryPage => {
          this.title = categoryPage.category;
          this.page = categoryPage.page;
        });
    }

  }

  goToProductDetailPage(productInfo) {
      this.router.navigate(['/product/' + productInfo.id]);
  }*/

}
