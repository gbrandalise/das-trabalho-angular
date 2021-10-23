import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { OrderItem } from 'src/app/shared/models/order-item.model';
import { OrderItemService } from './order-item.service';

describe('OrderItemService', () => {
  let service: OrderItemService;
  let httpSpy: jasmine.SpyObj<HttpClient>;
  let orderItem: OrderItem = new OrderItem(1, 10);

  beforeEach(() => {
    httpSpy = jasmine.createSpyObj('HttpClient', ['get', 'post', 'put', 'delete'])
    TestBed.configureTestingModule({
      providers:[
        OrderItemService,
        { provide: HttpClient, useValue: httpSpy}
      ]
    });
    service = TestBed.inject(OrderItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should list items from order', (done) => {
    let orderItems: OrderItem[] = [{...orderItem},{...orderItem},{...orderItem}];
    httpSpy.get.and.returnValue(of(orderItems));
    service.findByOrderId(1).subscribe(
      result => {
        expect(result).toEqual(orderItems);
        done();
      },
      done.fail
    );
    expect(httpSpy.get.calls.count()).toBe(1);
  });
});
