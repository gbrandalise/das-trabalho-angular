import { ProductsComponent } from './pages/products/products.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductComponent } from './pages/product/product.component';
import { ProductResolver } from './resolvers/product.resolver';

const routes: Routes = [

  {
    path: 'register/:id',
    component: ProductComponent,
    resolve: {
      product: ProductResolver,
    },
  },
  {path: 'register', component : ProductComponent,
},
    {path: '', component : ProductsComponent,
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
  })
  export class ProductRoutingModule {}
