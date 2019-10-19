import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Product} from '../model/product.model';

export const PRODUCT_API = 'http://localhost:8080/api/v1/products';

@Injectable()
export class ProductService {

  constructor(private httpClient: HttpClient) {
  }

  getProducts() {
    return this.httpClient.get<Product[]>(PRODUCT_API, {withCredentials: true});
  }
}
