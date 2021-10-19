import { Product } from './../../../../shared/models/product.model';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { ProductService } from './../../services/product.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {

  constructor(
    private service: ProductService,
    private notification: NzNotificationService
  ) { }

  products: Product[] = [];
  loading = false;
  size = 8;

  ngOnInit(): void {
    this.loading = true;
    this.getAll();
  }

  getAll(){
    this.service.getAll().subscribe(
      (products: Product[]) => {
        this.products = [...products];
      },
      (err) => {
        this.notification.create('error', 'Error', err);
      },
      () => {
        this.loading = false;
      }

    );
  }

  handleDelete(id: number): void {
    console.log("to do")
  }

}
