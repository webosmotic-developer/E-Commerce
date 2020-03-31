import {Component, Input, OnInit} from '@angular/core';
import {NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-product-display',
  templateUrl: './product-display.component.html',
  styleUrls: ['./product-display.component.css']
})
export class ProductDisplayComponent implements OnInit {

  constructor(config: NgbRatingConfig,
              private route: Router,
              private router: ActivatedRoute) {
    // customize default values of ratings used by this component tree
    config.max = 5;
    config.readonly = false;
  }

  currentRate = 3.5;

  @Input()
  productsData: any;

  @Input()
  title: string;

  ngOnInit() {
  }

  productDetails(id) {
    console.log(id);
    this.route.navigate(['/product', id], { relativeTo: this.router} );
  }

}
