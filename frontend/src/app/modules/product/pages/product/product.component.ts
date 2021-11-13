import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Subscription } from 'rxjs';
import { Product } from 'src/app/shared/models/product.model';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {
  subscription = new Subscription();
  form!: FormGroup;
  product: Product = new Product();
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private service: ProductService,
    private notification: NzNotificationService,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.subscription = this.route.data.subscribe((data) =>
    {
      if(data.product){
        this.product = data.product;
      }
    });
    this.form = this.fb.group({
      id: [this.product.id || '', [Validators.required]],
      description: [this.product.description || '', [Validators.required]]
    })
  }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  save(productData: Product): void {
    productData.id = this.product.id;
    this.service.save(productData).subscribe(
      (_product: Product) => {
        this.notification.create(
          'success',
          'Sucesso!',
          'Produto salvo com sucesso.'
        );
        this.router.navigate(['/product']);
      },
      (err) => {
        console.error(err);
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
    } else {
      this.save(this.form.value as Product);
    }
  }

}
