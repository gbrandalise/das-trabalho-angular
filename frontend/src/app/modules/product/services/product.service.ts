import { Endpoints } from './endpoints';

import { Product } from './../../../shared/models/product.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Product[]>{
    return this.http.get<Product[]>(Endpoints.productUrl);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${Endpoints.productUrl}/${id}`);
  }
}
