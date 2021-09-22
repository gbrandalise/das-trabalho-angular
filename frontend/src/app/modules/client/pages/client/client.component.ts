import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Client } from 'src/app/shared/models/client.model';
import { ClientService } from '../../services/client.service';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.scss'],
})
export class ClientComponent implements OnInit {
  form!: FormGroup;
  constructor(
    private fb: FormBuilder,
    private service: ClientService,
    private notification: NzNotificationService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      cpf: [null, [Validators.required]],
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
    });
  }

  save(client: Client): void {
    this.service.save(client).subscribe(
      (_client: Client) => {
        this.notification.create(
          'success',
          'Sucesso!',
          'Cliente salvo com sucesso.'
        );
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
