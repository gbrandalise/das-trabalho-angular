import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { forkJoin, Observable } from 'rxjs';
import { ClientService } from 'src/app/modules/client/services/client.service';
import { OrderItemService } from 'src/app/modules/order-item/services/order-item.service';
import { ProductService } from 'src/app/modules/product/services/product.service';
import { Client } from 'src/app/shared/models/client.model';
import { OrderItem } from 'src/app/shared/models/order-item.model';
import { Product } from 'src/app/shared/models/product.model';
import { PurchaseOrder } from 'src/app/shared/models/purchase-order.model';
import { PurchaseOrderService } from '../../services/purchase-order.service';

@Component({
  selector: 'app-purchase-order',
  templateUrl: './purchase-order.component.html',
  styleUrls: ['./purchase-order.component.scss'],
})
export class PurchaseOrderComponent implements OnInit {

  form!: FormGroup;
  purchaseOrder: PurchaseOrder = new PurchaseOrder();
  clients: Client[] = [];
  now: string = new Date().toISOString().slice(0, 16);
  loading: boolean = false;
  products: Product[] = [];
  orderItems: OrderItem[] = [];
  orderItem: OrderItem = new OrderItem();
  formOrderItem!: FormGroup;
  size = 8;

  constructor(
    private fb: FormBuilder,
    private service: PurchaseOrderService,
    private notification: NzNotificationService,
    private router: Router,
    private clientService: ClientService,
    private productService: ProductService,
    private orderItemService: OrderItemService,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],
      client: [null, [Validators.required]],
      date: [null],
    });
    this.formOrderItem = this.fb.group({
      product: [null, [Validators.required]],
      quantity: [null, [Validators.required]],
    })
    this.findAllClients();
    this.findAllProducts();
  }

  findAllClients() {
    this.clientService.getAll().subscribe(data => {
      this.clients = data;
      this.findOrder();
    });
  }

  findAllProducts() {
    this.loading = true;
    this.productService.getAll().subscribe(data => {
      this.loading = false;
      this.products = data;
    });
  }

  findOrder() {
    this.route.data.subscribe((data) => {
      if (data.purchaseOrder) {
        this.purchaseOrder = data.purchaseOrder;
        this.setClient();
        this.form.setValue(this.purchaseOrder);
        this.findOrderItemsByOrder();
      }
    });
  }

  setClient() {
    this.purchaseOrder.client = this.clients.find(c => c.id == this.purchaseOrder.client!.id);
  }

  findOrderItemsByOrder() {
    this.orderItemService.findByOrderId(this.purchaseOrder.id!).subscribe(
      (orderItems: OrderItem[]) => this.orderItems = orderItems,
      (err) => {
        console.error(err.error);
        this.notification.create('error', 'Error', err.error)
      }
    );
  }

  save(purchaseOrderData: PurchaseOrder): void {
    purchaseOrderData.id = this.purchaseOrder.id;
    this.service.save(purchaseOrderData).subscribe(
      (_purchaseOrder: PurchaseOrder) => {
        if (_purchaseOrder.id != null) {
          let observables: Observable<OrderItem>[] = [];
          this.orderItems.forEach((orderItem) => {
            orderItem.id = undefined;
            orderItem.order = _purchaseOrder;
            observables.push(this.orderItemService.save(orderItem));
          });
          forkJoin(observables).subscribe(() => {
            this.notification.create(
              'success',
              'Sucesso!',
              'Pedido salvo com sucesso.'
            );
            this.router.navigate(['/purchase-order']);
          });
        }
      },
      (err) => {
        console.error(err.error);
        this.notification.create('error', 'Error', err.error);
      }
    );
  }

  submitForm(): void {
    for (const i in this.form.controls) {
      if (this.form.controls.hasOwnProperty(i)) {
        this.form.controls[i].markAsDirty();
        this.form.controls[i].updateValueAndValidity();
      }
    }
    if (this.form.invalid) {
      this.notification.create(
        'error',
        'Error',
        'Por favor preencher campos obrigatórios.'
      );
    } else {
      if (this.orderItems.length == 0) {
        this.notification.create(
          'error',
          'Error',
          'Por favor adicionar pelo menos um produto.'
        );
      } else {
        this.save(this.form.value as PurchaseOrder);
      }
    }
  }

  getLabelClient(client: Client): string {
    return `${client.cpf} -  ${client.firstName} ${client.lastName}`;
  }

  submitFormOrderItem() {
    for (const i in this.formOrderItem.controls) {
      if (this.formOrderItem.controls.hasOwnProperty(i)) {
        this.formOrderItem.controls[i].markAsDirty();
        this.formOrderItem.controls[i].updateValueAndValidity();
      }
    }
    if (this.formOrderItem.invalid) {
      this.notification.create(
        'error',
        'Error',
        'Por favor preencher campos obrigatórios.'
      );
    } else {
      this.addOrderItem();
      this.formOrderItem.reset();
    }
  }

  addOrderItem() {
    let orderItem: OrderItem = this.formOrderItem.value as OrderItem;
    this.orderItems = [
      ...this.orderItems,
      orderItem
    ];
  }

  getProducts(): Product[] {
    return this.products.filter(p => !this.orderItems.some(o => o.product!.id == p.id));
  }

  deleteOrderItem(data: OrderItem) {
    this.orderItems = this.orderItems.filter(o => o != data);
  }

  changeQuantity(data: OrderItem) {
    if (data.quantity! < 1) {
      data.quantity = 1;
    }
  }
}
