import 'dart:convert';

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
      return PurchaseOrder.fromJsonList(utf8.decode(response.bodyBytes));
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
      return PurchaseOrder.fromJsonList(utf8.decode(response.bodyBytes));
    } else {
      throw Exception('Error code: ${response.statusCode}');
    }
  }

  Future<PurchaseOrder> save(PurchaseOrder purchaseOrder) async {
    if (purchaseOrder.id != null) {
      return update(purchaseOrder);
    }
    return create(purchaseOrder);
  }

  Future<PurchaseOrder> create(PurchaseOrder purchaseOrder) async {
    http.Response response = await http.post(
      Uri.http(Environment.API_URL, RESOURCE_URL),
      headers: Environment.HEADERS,
      body: purchaseOrder.toJson()
    );
    if (response.statusCode == 201) {
      return PurchaseOrder.fromJson(utf8.decode(response.bodyBytes));
    } else {
      throw Exception('Error code: ${response.statusCode}');
    }
  }

  Future<PurchaseOrder> update(PurchaseOrder purchaseOrder) async {
    http.Response response = await http.put(
      Uri.http(Environment.API_URL, '$RESOURCE_URL/${purchaseOrder.id}'),
      headers: Environment.HEADERS,
      body: purchaseOrder.toJson()
    );
    if (response.statusCode == 200) {
      return PurchaseOrder.fromJson(utf8.decode(response.bodyBytes));
    } else {
      throw Exception('Error code: ${response.statusCode}');
    }
  }

  Future<void> delete(int id) async {
    http.Response response = await http.delete(
      Uri.http(Environment.API_URL, '$RESOURCE_URL/$id'),
      headers: Environment.HEADERS,
    );
    if (response.statusCode != 200) {
      throw Exception('Error code: ${response.statusCode}');
    }
  }
}