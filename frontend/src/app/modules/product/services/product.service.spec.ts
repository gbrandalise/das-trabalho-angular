import { of } from 'rxjs';
import { Product } from './../../../shared/models/product.model';
import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { ProductService } from './product.service';

describe('ProductService', () => {
  let service: ProductService;
  let httpSpy: jasmine.SpyObj<HttpClient>;
  let product: Product = new Product(1, "produto 1");
  beforeEach(() => {
    httpSpy = jasmine.createSpyObj('HttpClient', ['get', 'post', 'put', 'delete'])
    TestBed.configureTestingModule({
      providers:[
        ProductService,
        { provide: HttpClient, useValue: httpSpy}
      ]
    });
    service = TestBed.inject(ProductService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should list all products', (done) => {
    let products: Product[] = [{...product},{...product},{...product}];
    httpSpy.get.and.returnValue(of(products));
    service.getAll().subscribe(
      result => {
        expect(result).toEqual(products);
        done();
      },
      done.fail
    );
    expect(httpSpy.get.calls.count()).toBe(1);
  })

  it('should delete product by id', (done) => {
    httpSpy.delete.and.returnValue(of(null));
    service.delete(1).subscribe(
      () => done(),
      done.fail
    );
    expect(httpSpy.delete.calls.count()).toBe(1);
  });

  it('should get product by id', (done) => {
    httpSpy.get.and.returnValue(of(product));
    service.get(1).subscribe(
      result => {
        expect(result).toEqual(product);
        done();
      },
      done.fail
    );
    expect(httpSpy.get.calls.count()).toBe(1);
  });

  it('should save old product', (done) => {
    httpSpy.put.and.returnValue(of(product));
    service.save(product).subscribe(
      result => {
        expect(result).toEqual(product);
        done();
      },
      done.fail
    );
    expect(httpSpy.put.calls.count()).toBe(1);
  });

  it('should save new product', (done) => {
    let productToSave: Product = {...product};
    productToSave.id = undefined;
    httpSpy.post.and.returnValue(of(product));
    service.save(productToSave).subscribe(
      result => {
        expect(result).toEqual(product);
        done();
      },
      done.fail
    );
    expect(httpSpy.post.calls.count()).toBe(1);
  });

});
