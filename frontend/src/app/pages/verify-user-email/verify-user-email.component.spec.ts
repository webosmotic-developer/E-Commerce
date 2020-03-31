import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VerifyUserEmailComponent } from './verify-user-email.component';

describe('VerifyUserEmailComponent', () => {
  let component: VerifyUserEmailComponent;
  let fixture: ComponentFixture<VerifyUserEmailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VerifyUserEmailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VerifyUserEmailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
