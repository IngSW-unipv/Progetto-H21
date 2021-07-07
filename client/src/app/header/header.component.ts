import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from '../auth/auth.service';

/**
 * @author Filippo Casarosa
 */
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {
  private adminSub: Subscription;
  isAuthenticated: boolean = false;

  constructor(private router: Router,
              private authService: AuthService) { }

  ngOnInit(): void {
    this.adminSub = this.authService.admin.subscribe(admin => {
    this.isAuthenticated = !!admin
    });
  }

  ngOnDestroy(): void {
    this.adminSub.unsubscribe();
  }

  onLogout(){
    this.authService.logout();
    window.location.reload();
  }
}
