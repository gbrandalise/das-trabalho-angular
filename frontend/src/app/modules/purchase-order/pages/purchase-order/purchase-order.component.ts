import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { ClientService } from 'src/app/modules/client/services/client.service';
import { Client } from 'src/app/shared/models/client.model';
import { PurchaseOrder } from 'src/app/shared/models/purchase-order.model';
import { PurchaseOrderService } from '../../services/purchase-order.service';

@Component({
  selector: 'app-purchase-order',
  templateUrl: './purchase-order.component.html',
  styleUrls: ['./purchase-order.component.scss'],
})
export class PurchaseOrderComponent implements OnInit {

  form!: FormGroup;
  purchaseOrder: PurchaseOrder = new PurchaseOrder();
  clients: Client[] = [];
  now: string = new Date().toISOString().slice(0, 16);

  constructor(
    private fb: FormBuilder,
    private service: PurchaseOrderService,
    private notification: NzNotificationService,
    private router: Router,
    private clientService: ClientService,
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      client: [null, [Validators.required]],
    });
    this.findAllClients();
  }

  findAllClients() {
    this.clientService.getAll().subscribe(data => {
      this.clients = data;
    });
  }

  save(purchaseOrderData: PurchaseOrder): void {
    this.service.save(purchaseOrderData).subscribe(
      (_purchaseOrder: PurchaseOrder) => {
        this.notification.create(
          'success',
          'Sucesso!',
          'Pedido salvo com sucesso.'
        );
        this.router.navigate(['/purchase-order']);
      },
      (err) => {
        console.error(err.error);
        this.notification.create('error', 'Error', err.error);
      }
    );
  }

  submitForm(): void {
    for (const i in this.form.controls) {
      if (this.form.controls.hasOwnProperty(i)) {
        this.form.controls[i].markAsDirty();
        this.form.controls[i].updateValueAndValidity();
      }
    }
    if (this.form.invalid) {
      this.notification.create(
        'error',
        'Error',
        'Por favor preencher campos obrigatórios.'
      );
    } else {
      this.save(this.form.value as PurchaseOrder);
    }
  }

  getLabelClient(client: Client): string {
    return `${client.cpf} -  ${client.firstName} ${client.lastName}`;
  }
}
