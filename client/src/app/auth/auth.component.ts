import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthResponseData, AuthService } from './auth.service';

/**
 * @author Filippo Casarosa
 */
@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {
  isLoginMode = true;
  isLoading = false;
  error: string = null;
  token: string;
  form: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  constructor(private authService: AuthService,
              private router: Router) {}

  ngOnInit(): void {
  }

  onSubmit(form: NgForm) {
    if (!form.valid){
      return;
    }
    const name = form.value.username;
    const password = form.value.password;

    let authObs: Observable<AuthResponseData>

    this.isLoading = true;

    authObs = this.authService.login(name, password);

    authObs.subscribe(
      resData => {
        console.log(resData);
        this.isLoading = false;
        this.token = resData.token;
        this.router.navigate(['/products'])
      },
      errorMessage => {
        console.log(errorMessage);
        this.error = errorMessage;
        this.isLoading = false;
      }
    );
    form.reset();
  }
}
