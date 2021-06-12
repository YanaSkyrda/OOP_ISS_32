import { KeycloakService } from "keycloak-angular";

export function initializeKeycloak(
  keycloak: KeycloakService
) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8180' + '/auth',
        realm: 'aero',
        clientId: 'client',
      }
    });
}
