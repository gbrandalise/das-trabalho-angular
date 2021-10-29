import { Endpoints } from './endpoints';

import { OrderItem } from '../../../shared/models/order-item.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OrderItemService {

  constructor(private http: HttpClient) { }

  findByOrderId(orderId: number): Observable<OrderItem[]>{
    return this.http.get<OrderItem[]>(`${Endpoints.orderItemUrl}/by-order-id/${orderId}`);
  }

  save(orderItem: OrderItem): Observable<OrderItem> {
    if (orderItem.id) {
      // TODO: update order item
    }
    return this.create(orderItem);
  }

  create(orderItem: OrderItem): Observable<OrderItem> {
    return this.http.post<OrderItem>(Endpoints.orderItemUrl, orderItem);
  }
}
