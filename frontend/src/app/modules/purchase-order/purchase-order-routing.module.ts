import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PurchaseOrderComponent } from './pages/purchase-order/purchase-order.component';
import { PurchaseOrdersComponent } from './pages/purchase-orders/purchase-orders.component';
import { PurchaseOrderResolver } from './resolvers/purchase-order.resolver';

const routes: Routes = [
  { path: '', component: PurchaseOrdersComponent },
  { path: 'register', component: PurchaseOrderComponent},
  { path: 'register/:id', component: PurchaseOrderComponent,
    resolve: {
      purchaseOrder: PurchaseOrderResolver,
    }
   },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PurchaseOrderRoutingModule { }
