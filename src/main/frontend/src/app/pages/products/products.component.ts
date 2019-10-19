import {Component, OnInit} from '@angular/core';
import {Product} from '../../model/product.model';
import {ProductService} from '../../service/product.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {

  products: Product[] = [];

  constructor(private productService: ProductService) {
  }

  ngOnInit() {
    this.initProdcuts();
  }

  initProdcuts() {
    this.productService.getProducts().subscribe(
      data => {
        this.products = data;
      },
      error => {
        console.log('Error occurred:');
        console.log(error);
        alert('Error occurred');
      }
    );
  }
}
