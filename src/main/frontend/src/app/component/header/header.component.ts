import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../service/auth/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(public authService: AuthService, private router: Router) {
  }

  ngOnInit() {
  }

  logout() {
    this.authService.logout().subscribe(
      () => {
        this.router.navigate(['/login']);
      },
      error => {
        console.log('Error occurred: ' + error != null && error.error != null ? error.error.message : error);
      },
      () => {
        location.reload();
      }
    );
  }
}
