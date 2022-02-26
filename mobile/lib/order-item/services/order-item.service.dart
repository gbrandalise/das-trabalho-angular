import 'dart:convert';

import 'package:das_angular_mobile/common/environment.dart';
import 'package:das_angular_mobile/order-item/order-item.model.dart';
import 'package:das_angular_mobile/purchase-order/purchase-order.model.dart';
import 'package:http/http.dart' as http;

class OrderItemService {

  // ignore: constant_identifier_names
  static const String RESOURCE_URL = '/order-items';

  Future<List<PurchaseOrder>> findByOrderId(int orderId) async {
    http.Response response = await http.get(
      Uri.http(Environment.API_URL, '$RESOURCE_URL/by-order-id/$orderId'),
      headers: Environment.HEADERS
    );
    if (response.statusCode == 200) {
      return PurchaseOrder.fromJsonList(utf8.decode(response.bodyBytes));
    } else {
      throw Exception('Error code: ${response.statusCode}');
    }
  }

  Future<OrderItem> save(OrderItem orderItem) async {
    return create(orderItem);
  }

  Future<OrderItem> create(OrderItem orderItem) async {
    http.Response response = await http.post(
      Uri.http(Environment.API_URL, RESOURCE_URL),
      headers: Environment.HEADERS,
      body: orderItem.toJson()
    );
    if (response.statusCode == 201) {
      return OrderItem.fromJson(utf8.decode(response.bodyBytes));
    } else {
      throw Exception('Error code: ${response.statusCode}');
    }
  }
}