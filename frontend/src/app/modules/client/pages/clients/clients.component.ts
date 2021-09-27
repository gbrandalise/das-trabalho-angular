import { Component, OnInit } from '@angular/core';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Client } from 'src/app/shared/models/client.model';
import { ClientService } from '../../services/client.service';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.scss'],
})
export class ClientsComponent implements OnInit {
  constructor(
    private service: ClientService,
    private notification: NzNotificationService
  ) {}

  clients: Client[] = [];
  loading = false;
  size = 8;

  ngOnInit(): void {
    this.loading = true;
    this.getAll();
  }

  getAll() {
    this.service.getAll().subscribe(
      (clients: Client[]) => {
        this.clients = [...clients];
      },
      (err) => {
        this.notification.create('error', 'Error', err);
      },
      () => {
        this.loading = false;
      }
    );
  }

  handleDelete(id: number): void {
    this.service.delete(id).subscribe(
      () => {
        this.notification.create(
          'success',
          'Sucesso!',
          'Cliente excluído com sucesso.'
        );
        this.getAll();
      },
      (err) => {
        console.log(err);
        this.notification.create('error', 'Error', err);
      }
    );
  }
}
