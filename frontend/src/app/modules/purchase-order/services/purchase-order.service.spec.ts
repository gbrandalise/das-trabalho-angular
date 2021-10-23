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

  it('should list all products', (done) => {
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
  })
});
