import 'dart:convert';

import 'package:das_angular_mobile/product/product.model.dart';
import 'package:http/http.dart' as http;
import 'package:das_angular_mobile/common/environment.dart';

class ProductService {
  // ignore: constant_identifier_names
  static const String PRODUCT_URL = '/products';

  Future<List<Product>> findAll() async {
    http.Response response = await http.get(
        Uri.http(Environment.API_URL, PRODUCT_URL),
        headers: Environment.HEADERS);
    if (response.statusCode == 200) {
      return Product.fromJsonList(utf8.decode(response.bodyBytes));
    } else {
      throw Exception('Error code: ${response.statusCode}');
    }
  }

  Future<Product> save(Product product) async {
    if (product.id != null) {
      //UPDATE
    }
    return create(product);
  }

  Future<Product> create(Product product) async {
    http.Response response = await http.post(
        Uri.http(Environment.API_URL, PRODUCT_URL),
        headers: Environment.HEADERS,
        body: product.toJson());
    if (response.statusCode == 201) {
      return Product.fromJson(utf8.decode(response.bodyBytes));
    } else {
      throw Exception('Error code: ${response.statusCode}');
    }
  }
}
