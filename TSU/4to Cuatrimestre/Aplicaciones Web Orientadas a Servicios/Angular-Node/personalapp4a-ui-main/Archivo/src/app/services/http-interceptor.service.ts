import {
    HttpInterceptor,
    HttpHandler,
    HttpEvent,
    HttpRequest
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { APP_URL } from "./base-urls";

@Injectable()
export class CustomHttInterceptorService
    implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token = localStorage.getItem("token");
        const isLogged = !!token;
        const isValidUrl = req.url.startsWith(APP_URL);
        if (isLogged && isValidUrl) {
            req = req.clone({
                setHeaders: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                    Accept: 'application/json'
                }
            });
        }
        return next.handle(req);
    }
}