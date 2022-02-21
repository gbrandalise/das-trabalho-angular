import 'package:das_angular_mobile/product/product.model.dart';
import 'package:http/http.dart' as http;
import 'package:das_angular_mobile/common/environment.dart';

class ProductService {
  static const String PRODUCT_URL = '/products';

  Future<List<Product>> findAll() async {
    http.Response response = await http.get(
        Uri.http(Environment.API_URL, PRODUCT_URL),
        headers: Environment.HEADERS);
    if (response.statusCode == 200) {
      return Product.fromJsonList(response.body);
    } else {
      throw Exception('Error code: ${response.statusCode}');
    }
  }
}
