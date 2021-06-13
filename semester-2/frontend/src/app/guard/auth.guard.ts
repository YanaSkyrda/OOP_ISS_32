import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {KeycloakAuthGuard, KeycloakService} from "keycloak-angular";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard extends KeycloakAuthGuard {

  constructor(
    protected readonly router: Router,
    protected readonly keycloak: KeycloakService
  ) {
    super(router, keycloak);
  }

  login() {

  }

  isUerAdmin() : Boolean {
    console.log(this.keycloak.getUserRoles())
    return this.keycloak.isUserInRole('admin')
  }

  async getUsername() : Promise<string> {
    if (await this.keycloak.isLoggedIn()) {
      let profile = await this.keycloak.loadUserProfile(false)
      return profile.username
    } else {
      return 'user';
    }
  }

  async isAccessAllowed(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Promise<boolean | UrlTree> {

    if (!this.authenticated) {
      await this.keycloak.login({
        redirectUri: window.location.origin + state.url,
      });
    }

    return this.authenticated;
  }

  logout() {
    this.keycloak.logout(window.location.origin);
  }

}
