import { SharedModule } from '../../shared/shared.module';
import { PurchaseOrderRoutingModule } from './purchase-order-routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PurchaseOrdersComponent } from './pages/purchase-orders/purchase-orders.component';
import { PurchaseOrderComponent } from './pages/purchase-order/purchase-order.component';

@NgModule({
  declarations: [
    PurchaseOrdersComponent,
    PurchaseOrderComponent,
  ],
  imports: [
    CommonModule,
    PurchaseOrderRoutingModule,
    SharedModule
  ]
})
export class PurchaseOrderModule { }
