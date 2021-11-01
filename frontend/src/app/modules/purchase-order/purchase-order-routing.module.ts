import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PurchaseOrderComponent } from './pages/purchase-order/purchase-order.component';
import { PurchaseOrdersComponent } from './pages/purchase-orders/purchase-orders.component';

const routes: Routes = [
  { path: '', component: PurchaseOrdersComponent },
  { path: 'register', component: PurchaseOrderComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PurchaseOrderRoutingModule { }
