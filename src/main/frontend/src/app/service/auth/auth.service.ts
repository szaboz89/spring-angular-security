import {Injectable} from '@angular/core';

import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {AuthenticatedUser} from '../../model/authenticated-user.model';

export const AUTH_API = environment.authUrl;

export const STORAGE_AUTHENTICATED_KEY = 'springAngularSecurityAppAuthenticated';

@Injectable()
export class AuthService {

  authenticated = false;
  authenticatedUser: AuthenticatedUser = new AuthenticatedUser();

  constructor(private httpClient: HttpClient,
              private router: Router) {
    this.refreshAuthenticatedUser();
  }

  refreshAuthenticatedUser() {
    this.getAuthenticatedUser().subscribe(
      data => {
        this.authenticatedUser = data;
        this.authenticated = true;
        sessionStorage.setItem(STORAGE_AUTHENTICATED_KEY, 'true');
      },
      (error: HttpErrorResponse) => {
        console.log('User is not authenticated, error: ' + error.error.message);
        sessionStorage.clear();
        this.authenticated = false;
        this.authenticatedUser = new AuthenticatedUser();
        this.router.navigate(['/login'], {});
      }
    );
  }

  isUserAuthenticated(): boolean {
    return sessionStorage.getItem(STORAGE_AUTHENTICATED_KEY) === 'true' || this.authenticated;
  }

  isUserInRole(role: string): boolean {
    return this.authenticated && this.authenticatedUser != null
      && this.authenticatedUser.roles != null
      && this.authenticatedUser.roles.includes(role);
  }

  getAuthenticatedUser(): Observable<any> {
    const URL: string = AUTH_API + '/user-details';
    return this.httpClient.get(URL, {withCredentials: true});
  }

  login(username: string, password: string) {
    const URL: string = AUTH_API + '/login';
    const body = new HttpParams()
      .set('username', username)
      .set('password', password);
    return this.httpClient.post(URL,
      body.toString(),
      {
        headers: new HttpHeaders()
          .set('Content-Type', 'application/x-www-form-urlencoded'),
        responseType: 'text',
        withCredentials: true
      }
    );
  }

  logout() {
    sessionStorage.clear();
    const URL: string = AUTH_API + '/logout';
    return this.httpClient.post(URL, '', {withCredentials: true});
  }
}
