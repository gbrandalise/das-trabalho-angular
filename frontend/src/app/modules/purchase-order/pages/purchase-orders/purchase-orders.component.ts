import { Component, OnInit } from '@angular/core';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { PurchaseOrder } from 'src/app/shared/models/purchase-order.model';
import { PurchaseOrderService } from '../../services/purchase-order.service';

@Component({
  selector: 'app-purchase-orders',
  templateUrl: './purchase-orders.component.html',
  styleUrls: ['./purchase-orders.component.scss']
})
export class PurchaseOrdersComponent implements OnInit {

  purchaseOrders: PurchaseOrder[] = [];
  loading = false;
  size = 8;

  constructor(
    private service: PurchaseOrderService,
    private notification: NzNotificationService
  ) { }

  ngOnInit(): void {
    this.findAll();
  }

  findAll() {
    this.loading = true;
    this.service.findAll().subscribe(
      (purchaseOrders: PurchaseOrder[]) => this.purchaseOrders = purchaseOrders,
      (err) => {
        console.error(err.error);
        this.notification.create('error', 'Error', err.error)
      },
      () => this.loading = false
    );
  }

}
