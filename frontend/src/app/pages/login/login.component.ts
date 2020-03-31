import {Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Role} from '../../enum/Role';
import {AuthService} from '../../services/Auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  isInvalid: boolean;
  isLogout: boolean;
  model: any = {
    username: '',
    password: '',
    remembered: false
  };
  returnUrl = '/';

  constructor(private userService: UserService,
              private router: Router,
              private route: ActivatedRoute,
              private authService: AuthService) {
  }

  ngOnInit() {
  }

  onSubmit() {
    console.log(this.model);
    this.authService.login(this.model);
    this.authService.currentUser.subscribe(user => {
      if (user) {
        console.log(user);
        if (user.role !== Role.Buyer) {
          this.returnUrl = '/seller';
        }
        this.router.navigate(['/']);
      } else {
        console.log(user);
        this.isInvalid = true;
      }
    });
  }
}
