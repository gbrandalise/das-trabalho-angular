import 'dart:convert';

import 'package:das_angular_mobile/product/product.model.dart';
import 'package:das_angular_mobile/purchase-order/purchase-order.model.dart';

class OrderItem {
  int? id;
  int? quantity;
  PurchaseOrder? order;
  Product? product;

  OrderItem({this.id, this.quantity, this.order, this.product});

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'quantity': quantity,
      'order': order!.toMap(),
      'product': product!.toMap(),
    };
  }

  static OrderItem fromMap(Map<String, dynamic> map) {
    return OrderItem(
      id: map['id'],
      quantity: map['quantity'],
      order: PurchaseOrder.fromMap(map['order']),
      product: Product.fromMap(map['product'])
    );
  }
  
  static List<OrderItem> fromMaps(List<Map<String, dynamic>> maps) {
    return List.generate(maps.length, (i) {
      return OrderItem.fromMap(maps[i]);
    });
  }

  static OrderItem fromJson(String json) => OrderItem.fromMap(jsonDecode(json));

  static List<OrderItem> fromJsonList(String json) {
    final parsed = jsonDecode(json).cast<Map<String, dynamic>>();
    return parsed.map<OrderItem>((map) => OrderItem.fromMap(map)).toList();
  }

  String toJson() => jsonEncode(toMap());
}