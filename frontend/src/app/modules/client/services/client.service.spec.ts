import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { Client } from 'src/app/shared/models/client.model';

import { ClientService } from './client.service';

describe('ClientService', () => {
  let service: ClientService;
  let httpClientSpy: jasmine.SpyObj<HttpClient>;
  let client: Client = new Client(1, '99999999999', 'test', 'test');

  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get', 'post', 'put', 'delete']);
    TestBed.configureTestingModule({
      providers:[
        ClientService,
        { provide: HttpClient, useValue: httpClientSpy }
      ]
    });
    service = TestBed.inject(ClientService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should save new client', (done) => {
    let clientToSave: Client = {...client};
    clientToSave.id = undefined;
    httpClientSpy.post.and.returnValue(of(client));
    service.save(clientToSave).subscribe(
      result => {
        expect(result).toEqual(client);
        done();
      },
      done.fail
    );
    expect(httpClientSpy.post.calls.count()).toBe(1);
  });

  it('should save old client', (done) => {
    httpClientSpy.put.and.returnValue(of(client));
    service.save(client).subscribe(
      result => {
        expect(result).toEqual(client);
        done();
      },
      done.fail
    );
    expect(httpClientSpy.put.calls.count()).toBe(1);
  });

  it('should list clients', (done) => {
    let clients: Client[] = [{...client},{...client},{...client}];
    httpClientSpy.get.and.returnValue(of(clients));
    service.getAll().subscribe(
      result => {
        expect(result).toEqual(clients);
        done();
      },
      done.fail
    );
    expect(httpClientSpy.get.calls.count()).toBe(1);
  });

  it('should get client by id', (done) => {
    httpClientSpy.get.and.returnValue(of(client));
    service.get(1).subscribe(
      result => {
        expect(result).toEqual(client);
        done();
      },
      done.fail
    );
    expect(httpClientSpy.get.calls.count()).toBe(1);
  });

  it('should delete client by id', (done) => {
    httpClientSpy.delete.and.returnValue(of(null));
    service.delete(1).subscribe(
      () => done(),
      done.fail
    );
    expect(httpClientSpy.delete.calls.count()).toBe(1);
  });
});
