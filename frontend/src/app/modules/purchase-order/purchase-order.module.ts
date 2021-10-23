import { SharedModule } from '../../shared/shared.module';
import { PurchaseOrderRoutingModule } from './purchase-order-routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PurchaseOrdersComponent } from './pages/purchase-orders/purchase-orders.component';

@NgModule({
  declarations: [
    PurchaseOrdersComponent
  ],
  imports: [
    CommonModule,
    PurchaseOrderRoutingModule,
    SharedModule
  ]
})
export class PurchaseOrderModule { }
