import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Product} from '../model/product.model';
import {environment} from '../../environments/environment';

export const PRODUCT_API = environment.apiUrl + '/products';

@Injectable()
export class ProductService {

  constructor(private httpClient: HttpClient) {
  }

  getProducts() {
    return this.httpClient.get<Product[]>(PRODUCT_API, {withCredentials: true});
  }
}
