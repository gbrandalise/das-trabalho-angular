import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { IconsProviderModule } from '../icons-provider.module';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzTypographyModule } from 'ng-zorro-antd/typography';
import { NzNotificationModule } from 'ng-zorro-antd/notification';
import { NzBreadCrumbModule } from 'ng-zorro-antd/breadcrumb';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzSpaceModule } from 'ng-zorro-antd/space';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    IconsProviderModule,
    NzLayoutModule,
    NzMenuModule,
    NzFormModule,
    NzInputModule,
    NzButtonModule,
    NzTypographyModule,
    NzNotificationModule,
    NzBreadCrumbModule,
    NzTableModule,
    NzSpaceModule,
  ],
  exports: [
    ReactiveFormsModule,
    FormsModule,
    IconsProviderModule,
    NzLayoutModule,
    NzMenuModule,
    NzFormModule,
    NzInputModule,
    NzButtonModule,
    NzTypographyModule,
    NzNotificationModule,
    NzBreadCrumbModule,
    NzTableModule,
    NzSpaceModule,
  ],
})
export class SharedModule {}
