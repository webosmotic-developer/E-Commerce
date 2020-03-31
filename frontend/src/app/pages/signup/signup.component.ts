import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';
import {SignUpRequest} from '../../models/SignUpRequest';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  user: SignUpRequest;
  passwordMatching = false;
  SingUpSuccess = false;
  signUpResponse = {};
  constructor( private location: Location,
               private userService: UserService,
               private router: Router) {
    this.user = new SignUpRequest();

  }



  ngOnInit() {
  }
  onSubmit() {
    console.log(this.user);
    if (this.user.password !== this.user.confirmPassword) {
      this.passwordMatching = true;
    } else {
      console.log("signup======" , this.user);
      delete this.user.confirmPassword;
      this.userService.signUp(this.user).subscribe((data) => {
        this.SingUpSuccess = true;
        this.signUpResponse = data;
        console.log(data);
      });
    }
  }

}
