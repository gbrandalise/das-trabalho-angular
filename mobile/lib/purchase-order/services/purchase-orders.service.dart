import 'package:das_angular_mobile/common/environment.dart';
import 'package:das_angular_mobile/purchase-order/purchase-order.model.dart';
import 'package:http/http.dart' as http;

class PurchaseOrderService {

  // ignore: constant_identifier_names
  static const String RESOURCE_URL = '/purchase-orders';

  Future<List<PurchaseOrder>> findAll() async {
    http.Response response = await http.get(
      Uri.http(Environment.API_URL, RESOURCE_URL),
      headers: Environment.HEADERS
    );
    if (response.statusCode == 200) {
      return PurchaseOrder.fromJsonList(response.body);
    } else {
      throw Exception('Error code: ${response.statusCode}');
    }
  }

  Future<List<PurchaseOrder>> findByClientCpf(String cpf) async {
    http.Response response = await http.get(
      Uri.http(Environment.API_URL, '$RESOURCE_URL/by-cpf/$cpf'),
      headers: Environment.HEADERS
    );
    if (response.statusCode == 200) {
      return PurchaseOrder.fromJsonList(response.body);
    } else {
      throw Exception('Error code: ${response.statusCode}');
    }
  }
}