import { Component, OnInit } from '@angular/core';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Observable } from 'rxjs';
import { OrderItemService } from 'src/app/modules/order-item/services/order-item.service';
import { OrderItem } from 'src/app/shared/models/order-item.model';
import { PurchaseOrder } from 'src/app/shared/models/purchase-order.model';
import { PurchaseOrderService } from '../../services/purchase-order.service';

@Component({
  selector: 'app-purchase-orders',
  templateUrl: './purchase-orders.component.html',
  styleUrls: ['./purchase-orders.component.scss']
})
export class PurchaseOrdersComponent implements OnInit {

  private findObservable?: Observable<PurchaseOrder[]>;

  purchaseOrders: PurchaseOrder[] = [];
  loading = false;
  size = 8;
  cpf: string = "";
  isVisibleOrderItems: boolean = false;
  orderItems: OrderItem[] = [];

  constructor(
    private service: PurchaseOrderService,
    private notification: NzNotificationService,
    private orderItemService: OrderItemService,
  ) { }

  ngOnInit(): void {
    this.findAll();
  }

  findAll() {
    this.findObservable = this.service.findAll();
    this.findPruchaseOrders();
  }

  filter() {
    if (this.cpf && this.cpf.trim() != '') {
      this.findByClientCpf();
    } else {
      this.findAll();
    }
  }

  findByClientCpf() {
    this.findObservable = this.service.findByClientCpf(this.cpf);
    this.findPruchaseOrders();
  }

  private findPruchaseOrders() {
    this.loading = true;
    this.findObservable!.subscribe(
      (purchaseOrders: PurchaseOrder[]) => this.purchaseOrders = purchaseOrders,
      (err) => {
        console.error(err.error);
        this.notification.create('error', 'Error', err.error)
      },
      () => this.loading = false
    );
  }

  showOrderItems(orderId: number) {
    this.isVisibleOrderItems = true;
    this.findOrderItems(orderId);
  }

  findOrderItems(orderId: number) {
    this.loading = true;
    this.orderItemService.findByOrderId(orderId).subscribe(
      (orderItems: OrderItem[]) => this.orderItems = orderItems,
      (err) => {
        console.error(err.error);
        this.notification.create('error', 'Error', err.error)
      },
      () => this.loading = false
    );
  }

  hideOrderItems() {
    this.isVisibleOrderItems = false;
  }
  getAll() {
    this.service.findAll().subscribe(
      (purchaseOrders: PurchaseOrder[]) =>{
        this.purchaseOrders =[...purchaseOrders]
      },
      (err) =>{
        this.notification.create('error','Error', err);
      },
      ()=>{
        this.loading=false;
      }
    )
  }
  handleDelete(id: number): void {
    this.service.delete(id).subscribe(
      () => {
        this.notification.create(
          'success',
          'Sucesso!',
          'Pedido excluído com sucesso.'
        );
        this.getAll();
      },
      (err) => {
        console.error(err.error);
        this.notification.create('error', 'Error', err.error);
      }
    );
  }
}
