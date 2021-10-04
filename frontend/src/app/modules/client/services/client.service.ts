import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Client } from 'src/app/shared/models/client.model';
import { Endpoints } from './endpoints';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  constructor(private http: HttpClient) {}

  save(client: Client): Observable<Client> {
    if (client.id) {
      return this.update(client);
    }
    return this.create(client);
  }

  create(client: Client): Observable<Client> {
    return this.http.post<Client>(Endpoints.clientUrl, client);
  }

  update(client: Client): Observable<Client> {
    return this.http.put<Client>(`${Endpoints.clientUrl}/${client.id}`, client);
  }

  getAll(): Observable<Client[]> {
    return this.http.get<Client[]>(Endpoints.clientUrl);
  }

  get(id: number): Observable<Client> {
    return this.http.get<Client>(`${Endpoints.clientUrl}/${id}`);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${Endpoints.clientUrl}/${id}`);
  }
}
