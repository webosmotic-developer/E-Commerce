import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-verify-user-email',
  templateUrl: './verify-user-email.component.html',
  styleUrls: ['./verify-user-email.component.css']
})
export class VerifyUserEmailComponent implements OnInit {
  token = '';
  show = false;
  message = '';
  constructor( private route: ActivatedRoute,
               private userService: UserService) { }

  ngOnInit() {
    if (this.route.snapshot.queryParamMap.get('token')) {
      console.log(this.route.snapshot.queryParamMap.get('token'));
      this.verifyUser(this.route.snapshot.queryParamMap.get('token'));
    }
  }

  verifyUser(token: string){
    console.log('token==========' , token);
    this.userService.verifyUser(token).subscribe((user) => {
      console.log(user);
      this.show = true;
      if (user != null) {
        this.message = user;
      } else {
        this.message = 'User verification failed';
      }
    });
  }


}
