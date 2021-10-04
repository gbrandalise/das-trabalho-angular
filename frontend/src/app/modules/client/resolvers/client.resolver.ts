import { Injectable } from '@angular/core';
import {
  Router,
  Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot,
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { Client } from 'src/app/shared/models/client.model';
import { ClientService } from '../services/client.service';

@Injectable({
  providedIn: 'root',
})
export class ClientResolver implements Resolve<Client> {
  constructor(private service: ClientService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    _state: RouterStateSnapshot
  ): Observable<Client> | Promise<Client> | Client {
    if (route.params.id) {
      return this.service.get(route.params.id);
    }
    return new Client();
  }
}
