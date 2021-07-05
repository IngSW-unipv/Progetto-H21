import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { BehaviorSubject, throwError } from "rxjs";
import { catchError, tap } from "rxjs/operators";
import { MessageService } from "../shared/services/message.service";
import { Admin } from "./admin.model";
import { Credential } from "./credential.model";

/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
// nomi del backend
export interface AuthResponseData {
  id: number;
  credential: Credential;
  token: string;
  expireDate: Date;
}

@Injectable({providedIn: 'root'})
export class AuthService{
  admin = new BehaviorSubject<Admin>(null);
  private tokenExpirationTimer: any;

  constructor(private http: HttpClient,
              private router: Router,
              private messageService: MessageService) {}

  login(name: string, password: string){
    return this.http.post<AuthResponseData>(
      'http://localhost:8080/login',
      {
        name: name,
        password: password,
        returnSecureToken: true
      }
    )
    .pipe(catchError(this.handleError),
      tap(resData => {
        this.handleAuthentication(
          resData.id,
          resData.credential,
          resData.token,
          resData.expireDate
        );
      }),
      tap((data) => console.log('token: ' + data.token))
    );
  }

  autoLogin() {
    const adminData: {
      id: number;
      name: string;
      _token: string;
      _tokenExpireDate: string;
    } = JSON.parse(localStorage.getItem('adminData'));
    console.log(adminData);
    if (!adminData) {
      return;
    }
    const loadedAdmin = new Admin(
      adminData.id,
      adminData.name,
      adminData._token,
      new Date (adminData._tokenExpireDate)
    );
    console.log(loadedAdmin);
    if (loadedAdmin.token) {
      this.admin.next(loadedAdmin);
      const expirationDuration =
        new Date(adminData._tokenExpireDate).getTime() - new Date().getTime();
      this.autoLogout(expirationDuration);
    }
  }

  logout(){
    const url = `http://localhost:8080/logout`
    return this.http.get<boolean>(url).subscribe(_ => {
      this.admin.next(null);
      this.router.navigate(['/auth']);
      localStorage.removeItem('adminData');
      if (this.tokenExpirationTimer) {
        clearTimeout(this.tokenExpirationTimer);
      }
      this.tokenExpirationTimer = null;
    });


  }

  autoLogout(expirationDuration: number) {
    console.log(expirationDuration);
    this.tokenExpirationTimer = setTimeout(() => {
      this.logout();
    }, expirationDuration);

  }

  private handleAuthentication(
    id: number,
    credential: Credential,
    token: string,
    expireDate: Date
  ){
    const admin = new Admin(
      id,
      credential.name,
      token,
      expireDate
    );
    this.admin.next(admin);
    const expirationDuration =
      new Date(expireDate).getTime() - new Date().getTime()
    this.autoLogout(expirationDuration);
    localStorage.setItem('adminData', JSON.stringify(admin));
  }

  private handleError(errorRes: HttpErrorResponse){
    let errorMessage = 'ACCESSO NEGATO';
    if (!errorRes.error || !errorRes.error.error) {
      return throwError(errorMessage);
    }
    switch (errorRes.error.error.message) {
      case 'NOT_FOUND':
        errorMessage = 'This email does not exist';
        break;
      case 'UNAUTHORIZED':
        errorMessage = 'This password is not correct';
        break;
    };
    return throwError(errorMessage);
  }

  private log(message: string) {
    this.messageService.add(`Auth: ${message}`);
  }
}
