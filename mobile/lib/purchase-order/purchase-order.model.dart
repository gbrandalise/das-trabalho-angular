import 'dart:convert';
import 'package:das_angular_mobile/client/client.model.dart';

class PurchaseOrder {
  int? id;
  DateTime? date;
  Client? client;

  PurchaseOrder(this.id, this.date, this.client);

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'date': date,
      'client': client,
    };
  }

  static PurchaseOrder fromMap(Map<String, dynamic> map) {
    return PurchaseOrder(
      map['id'],
      DateTime.parse(map['date']),
      Client.fromMap(map['client']),
    );
  }
  
  static List<PurchaseOrder> fromMaps(List<Map<String, dynamic>> maps) {
    return List.generate(maps.length, (i) {
      return PurchaseOrder.fromMap(maps[i]);
    });
  }

  static PurchaseOrder fromJson(String json) => PurchaseOrder.fromMap(jsonDecode(json));

  static List<PurchaseOrder> fromJsonList(String json) {
    final parsed = jsonDecode(json).cast<Map<String, dynamic>>();
    return parsed.map<PurchaseOrder>((map) => PurchaseOrder.fromMap(map)).toList();
  }

  String toJson() => jsonEncode(toMap());
}