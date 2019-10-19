import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService, STORAGE_AUTHENTICATED_KEY} from '../../service/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService,
              private router: Router) {
  }

  ngOnInit() {
  }

  login(form: NgForm) {
    const username = form.value.username;
    const password = form.value.password;
    this.authService.login(username, password).subscribe(
      () => {
        sessionStorage.setItem(STORAGE_AUTHENTICATED_KEY, 'true');
        this.authService.authenticated = true;
        this.authService.refreshAuthenticatedUser();
        this.router.navigate(['/home']);
      },
      error => {
        console.log('Login error');
        console.log(error);
        alert('Login error');
        this.authService.refreshAuthenticatedUser();
      }
    );
  }
}
