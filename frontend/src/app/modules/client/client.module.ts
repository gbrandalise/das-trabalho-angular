import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClientRoutingModule } from './client-routing.module';
import { ClientComponent } from './pages/client/client.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { ClientsComponent } from './pages/clients/clients.component';

@NgModule({
  declarations: [ClientComponent, ClientsComponent],
  imports: [CommonModule, ClientRoutingModule, SharedModule],
})
export class ClientModule {}
