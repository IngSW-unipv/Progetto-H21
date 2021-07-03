import { HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpParams, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { exhaustMap, take } from "rxjs/operators";
import { AuthService } from "./auth.service";

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return this.authService.admin.pipe(
      take(1),
      exhaustMap(admin => {
        if (!admin) {
          return next.handle(req)
        }
        const modifiedReq = req.clone({
          // params: new HttpParams().set('token', admin.token),
          headers: new HttpHeaders().set('token', admin.token)
        })
        return next.handle(modifiedReq)
      })
    );
  }
}
