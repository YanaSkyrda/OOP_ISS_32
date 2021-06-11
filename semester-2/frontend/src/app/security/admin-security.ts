import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {AuthService} from '../service/auth-service';
import {Observable} from 'rxjs';

@Injectable({providedIn: 'root'})
export class AdminSecurity implements CanActivate {
  constructor(
    private router: Router,
    private authService: AuthService
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const currentUser = this.authService.currentUserValue;
    console.log(currentUser.role);
    if (currentUser.role === 'ADMIN') {
      // logged in the system
      return true;
    }
    // not logged in so redirect to login page with the return url
    this.router.navigate(['/logout'], {queryParams: {returnUrl: state.url}});
    return false;
  }

}
