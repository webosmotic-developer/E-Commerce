import { Injectable } from '@angular/core';
import {UserService} from '../user.service';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import {JwtResponse} from '../../models/JwtResponse';
import {CookieService} from 'ngx-cookie-service';
import {HttpErrorResponse} from '@angular/common/http';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public nameTerms:  BehaviorSubject<string>;
  public name$: Observable<string>;
  public currentUserSubject: BehaviorSubject<JwtResponse>;
  public currentUser: Observable<JwtResponse>;
  constructor(private userService: UserService,
              private cookieService: CookieService,
              private router: Router) {
    const memo = localStorage.getItem('currentUser');
    cookieService.set('currentUser', memo);
    this.currentUserSubject = new BehaviorSubject<JwtResponse>(JSON.parse(memo));
    this.nameTerms = new BehaviorSubject<string>(memo != null ? JSON.parse(memo).name : null);
    this.currentUser = this.currentUserSubject.asObservable();
    this.name$ = this.nameTerms.asObservable();
  }

  login (loginForm: any): any {
    this.userService.login(loginForm).subscribe((user) => {
      console.log(user);
      if (user && user.token) {
        this.cookieService.set('currentUser', JSON.stringify(user));
        localStorage.setItem('currentUser', JSON.stringify(user));
        console.log((user.name));
        this.nameTerms.next(user.name);
        this.currentUserSubject.next(user);
      }
    }, (err: HttpErrorResponse) => {
      return null;
    });
  }

  logOut() {
    this.nameTerms.next(null);
    this.currentUserSubject.next(null);
    localStorage.removeItem('currentUser');
    this.cookieService.delete('currentUser');
    this.router.navigate(['/']);
  }

}
