import { Component, OnInit } from '@angular/core';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Observable } from 'rxjs';
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


  constructor(
    private service: PurchaseOrderService,
    private notification: NzNotificationService
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

  showOrderItems() {
    this.isVisibleOrderItems = true;
  }

  hideOrderItems() {
    this.isVisibleOrderItems = false;
  }

}
