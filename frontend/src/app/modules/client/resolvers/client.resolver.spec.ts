import { TestBed } from '@angular/core/testing';

import { ClientResolver } from './client.resolver';

describe('ClientResolver', () => {
  let resolver: ClientResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(ClientResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
