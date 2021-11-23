import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { PurchaseOrder } from 'src/app/shared/models/purchase-order.model';
import { PurchaseOrderService } from './purchase-order.service';


describe('PurchaseOrderService', () => {
  let service: PurchaseOrderService;
  let httpSpy: jasmine.SpyObj<HttpClient>;
  let purchaseOrder: PurchaseOrder = new PurchaseOrder(1, new Date());

  beforeEach(() => {
    httpSpy = jasmine.createSpyObj('HttpClient', ['get', 'post', 'put', 'delete'])
    TestBed.configureTestingModule({
      providers:[
        PurchaseOrderService,
        { provide: HttpClient, useValue: httpSpy}
      ]
    });
    service = TestBed.inject(PurchaseOrderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should list all pruchase orders', (done) => {
    let purchaseOrders: PurchaseOrder[] = [{...purchaseOrder},{...purchaseOrder},{...purchaseOrder}];
    httpSpy.get.and.returnValue(of(purchaseOrders));
    service.findAll().subscribe(
      result => {
        expect(result).toEqual(purchaseOrders);
        done();
      },
      done.fail
    );
    expect(httpSpy.get.calls.count()).toBe(1);
  });

  it('should list purchase orders by client cpf', (done) => {
    let purchaseOrders: PurchaseOrder[] = [{...purchaseOrder},{...purchaseOrder},{...purchaseOrder}];
    httpSpy.get.and.returnValue(of(purchaseOrders));
    service.findByClientCpf('99999999999').subscribe(
      result => {
        expect(result).toEqual(purchaseOrders);
        done();
      },
      done.fail
    );
    expect(httpSpy.get.calls.count()).toBe(1);
  });

  it('should save new order', (done) => {
    let purchaseOrderToSave: PurchaseOrder = {...purchaseOrder};
    purchaseOrderToSave.id = undefined;
    httpSpy.post.and.returnValue(of(purchaseOrder));
    service.save(purchaseOrderToSave).subscribe(
      result => {
        expect(result).toEqual(purchaseOrder);
        done();
      },
      done.fail
    );
    expect(httpSpy.post.calls.count()).toBe(1);
  });

  it('should save old order', (done) => {
    let purchaseOrderToSave: PurchaseOrder = {...purchaseOrder};
    httpSpy.put.and.returnValue(of(purchaseOrder));
    service.save(purchaseOrderToSave).subscribe(
      result => {
        expect(result).toEqual(purchaseOrder);
        done();
      },
      done.fail
    );
    expect(httpSpy.put.calls.count()).toBe(1);
  });

  it('should delete order by id', (done) => {
    httpSpy.delete.and.returnValue(of(null));
    service.delete(1).subscribe(
      () => done(),
      done.fail
    );
    expect(httpSpy.delete.calls.count()).toBe(1);
  });

  it('should get purchase order by id', (done) => {
    httpSpy.get.and.returnValue(of(purchaseOrder));
    service.findById(1).subscribe(
      result => {
        expect(result).toEqual(purchaseOrder);
        done();
      },
      done.fail
    );
    expect(httpSpy.get.calls.count()).toBe(1);
  });
});
