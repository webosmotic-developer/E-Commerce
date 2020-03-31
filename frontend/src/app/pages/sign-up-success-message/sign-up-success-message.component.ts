import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-sign-up-success-message',
  templateUrl: './sign-up-success-message.component.html',
  styleUrls: ['./sign-up-success-message.component.css']
})
export class SignUpSuccessMessageComponent implements OnInit {

  constructor() { }

  @Input()
  message: any;

  @Input()
  verifyUser: any = false;

  ngOnInit() {
  }

}
