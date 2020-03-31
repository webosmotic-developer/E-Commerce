import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SignUpSuccessMessageComponent } from './sign-up-success-message.component';

describe('SignUpSuccessMessageComponent', () => {
  let component: SignUpSuccessMessageComponent;
  let fixture: ComponentFixture<SignUpSuccessMessageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SignUpSuccessMessageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignUpSuccessMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
