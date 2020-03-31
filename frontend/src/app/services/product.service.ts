import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {ProductInfo} from '../models/productInfo';
import {apiUrl} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private appUrl = `${apiUrl}`;
  private categoryUrl = `${apiUrl}/category`;

  constructor(private http: HttpClient) {
  }

  getRandomPage(): Observable<any> {
    const url = this.appUrl + '/product';
    return this.http.get(url);
  }

  getRecentlyAdded() : Observable<any>{
    const url = this.appUrl + '/product/recent';
    return this.http.get(url);
  }


  getMostSelling () : Observable<any> {
    const url = this.appUrl + '/product/mostselling';
    return this.http.get(url);
  }

  getRealtedProducts (id): Observable<any> {
    const url = this.appUrl + '/product/related?id=' + id;
    return this.http.get(url);
  }


  getCategoryInPage(categoryType: number, page: number, size: number): Observable<any> {
    const url = `${this.categoryUrl}/${categoryType}?page=${page}&size=${size}`;
    return this.http.get(url).pipe(
      // tap(data => console.log(data))
    );
  }

  getDetail(id: String): Observable<any> {
    const url = this.appUrl + '/product/' + id;
    console.log(url);
    return this.http.get<any>(url);
  }

  update(productInfo: ProductInfo): Observable<ProductInfo> {
    const url = `${apiUrl}/seller/product/${productInfo.productId}/edit`;
    return this.http.put<ProductInfo>(url, productInfo);
  }

  create(productInfo: ProductInfo): Observable<ProductInfo> {
    const url = `${apiUrl}/seller/product/new`;
    return this.http.post<ProductInfo>(url, productInfo);
  }


  delelte(productInfo: ProductInfo): Observable<any> {
    const url = `${apiUrl}/seller/product/${productInfo.productId}/delete`;
    return this.http.delete(url);
  }


  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
