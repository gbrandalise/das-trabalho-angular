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

  findByClientCpf(cpf: string): Observable<PurchaseOrder[]>{
    return this.http.get<PurchaseOrder[]>(`${Endpoints.purchaseOrderUrl}/by-cpf/${cpf}`);
  }

  save(purchaseOrder: PurchaseOrder): Observable<PurchaseOrder> {
    if (purchaseOrder.id) {
      // TODO: update purchase order
    }
    return this.create(purchaseOrder);
  }

  create(purchaseOrder: PurchaseOrder): Observable<PurchaseOrder> {
    return this.http.post<PurchaseOrder>(Endpoints.purchaseOrderUrl, purchaseOrder);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${Endpoints.purchaseOrderUrl}/${id}`);
  }
  getAll(): Observable<PurchaseOrder[]> {
    return this.http.get<PurchaseOrder[]>(Endpoints.purchaseOrderUrl);
  }
}
