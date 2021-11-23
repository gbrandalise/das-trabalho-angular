import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot, Resolve,
  RouterStateSnapshot
} from '@angular/router';
import { Observable } from 'rxjs';
import { PurchaseOrder } from 'src/app/shared/models/purchase-order.model';
import { PurchaseOrderService } from '../services/purchase-order.service';

@Injectable({
  providedIn: 'root'
})
export class PurchaseOrderResolver implements Resolve<PurchaseOrder> {

  constructor(private service: PurchaseOrderService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    _state: RouterStateSnapshot
  ): Observable<PurchaseOrder> | Promise<PurchaseOrder> | PurchaseOrder {
    if (route.params.id) {
      return this.service.findById(route.params.id);
    }
    return new PurchaseOrder();
  }
}
