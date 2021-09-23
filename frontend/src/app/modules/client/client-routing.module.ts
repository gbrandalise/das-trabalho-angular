import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientComponent } from './pages/client/client.component';
import { ClientsComponent } from './pages/clients/clients.component';
import { ClientResolver } from './resolvers/client.resolver';

const routes: Routes = [
  {
    path: 'register/:id',
    component: ClientComponent,
    resolve: {
      client: ClientResolver,
    },
  },
  { path: 'register', component: ClientComponent },
  { path: '', component: ClientsComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ClientRoutingModule {}
