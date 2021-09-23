import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Subscription } from 'rxjs';
import { Client } from 'src/app/shared/models/client.model';
import { ClientService } from '../../services/client.service';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.scss'],
})
export class ClientComponent implements OnInit {
  subscription = new Subscription();
  form!: FormGroup;
  client: Client = new Client();
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private service: ClientService,
    private notification: NzNotificationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.subscription = this.route.data.subscribe((data) => {
      if (data.client) {
        this.client = data.client;
      }
    });

    this.form = this.fb.group({
      cpf: [this.client.cpf || '', [Validators.required]],
      firstName: [this.client.firstName || '', [Validators.required]],
      lastName: [this.client.lastName || '', [Validators.required]],
    });

    console.log(this.client);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  save(clientData: Client): void {
    clientData.id = this.client.id;
    this.service.save(clientData).subscribe(
      (_client: Client) => {
        this.notification.create(
          'success',
          'Sucesso!',
          'Cliente salvo com sucesso.'
        );
        this.router.navigate(['/client']);
      },
      (err) => {
        console.log(err);
        this.notification.create('error', 'Error', err);
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
        'Por favor verifique seu formulário.'
      );
    }

    this.save(this.form.value as Client);
  }
}
