import { Endpoints } from './endpoints';

import { PurchaseOrder } from '../../../shared/models/purchase-order.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PurchaseOrderService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<PurchaseOrder[]>{
    return this.http.get<PurchaseOrder[]>(Endpoints.purchaseOrderUrl);
  }
}
