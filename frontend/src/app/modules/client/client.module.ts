import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClientRoutingModule } from './client-routing.module';
import { ClientComponent } from './pages/client/client.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [ClientComponent],
  imports: [CommonModule, ClientRoutingModule, SharedModule],
})
export class ClientModule {}
