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

  save(product: Product): Observable<Product> {
    if (product.id) {
      return this.update(product);
    }
    return this.create(product);
  }
  create(product: Product): Observable<Product> {
    return this.http.post<Product>(Endpoints.productUrl, product);
  }
  update(product: Product): Observable<Product> {
    return this.http.put<Product>(`${Endpoints.productUrl}/${product.id}`, product);
  }

  getAll(): Observable<Product[]>{
    return this.http.get<Product[]>(Endpoints.productUrl);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${Endpoints.productUrl}/${id}`);
  }

  get(id: number): Observable<Product> {
    return this.http.get<Product>(`${Endpoints.productUrl}/${id}`);
  }
}
