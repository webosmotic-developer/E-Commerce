import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {apiUrl} from '../../environments/environment';
import {BehaviorSubject, Observable, of, Subject} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {JwtResponse} from '../models/JwtResponse';
import {CookieService} from 'ngx-cookie-service';
import {User} from "../models/User";
import {SignUpRequest} from '../models/SignUpRequest';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  public nameTerms = new Subject<string>();
  public name$ = this.nameTerms.asObservable();
  private currentUserSubject: BehaviorSubject<JwtResponse>;
  public currentUser: Observable<JwtResponse>;
  constructor(private http: HttpClient,
              private cookieService: CookieService) {
    const memo = localStorage.getItem('currentUser');
    this.currentUserSubject = new BehaviorSubject<JwtResponse>(JSON.parse(memo));
    this.currentUser = this.currentUserSubject.asObservable();
    cookieService.set('currentUser', memo);
  }

  get currentUserValue() {
    return this.currentUserSubject.value;
  }


  login(loginForm): Observable<JwtResponse> {
    const url = `${apiUrl}/auth/login`;
    return this.http.post<JwtResponse>(url, loginForm);
  }

  logout() {
    this.currentUserSubject.next(null);
    localStorage.removeItem('currentUser');
    this.cookieService.delete('currentUser');
  }

  signUp(user: SignUpRequest): Observable<any> {
    console.log("user======", user);
    const url = `${apiUrl}/auth/signup`;
    return this.http.post<any>(url, user);
  }

  verifyUser(token: string): Observable<any> {
    console.log("user======", token);
    const url = `${apiUrl}/auth/verify-email?token=` + token;
    return this.http.get<any>(url).pipe(
      tap(data => data),
      catchError(this.handleError('verification failed', null))
    );
  }

  update(user: User): Observable<User> {
    const url = `${apiUrl}/profile`;
    return this.http.put<User>(url, user);    }

  get(email: string): Observable<User> {
    const url = `${apiUrl}/profile/${email}`;
    return this.http.get<User>(url);
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.log(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
