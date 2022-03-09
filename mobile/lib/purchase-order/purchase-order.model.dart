import 'dart:convert';
import 'package:das_angular_mobile/client/client.model.dart';
import 'package:intl/intl.dart';

class PurchaseOrder {
  int? id;
  DateTime? date;
  Client? client;

  PurchaseOrder({this.id, this.date, this.client});
  
  @override
  bool operator == (dynamic other) =>
      other != null && other is PurchaseOrder && id == other.id;

  @override
  int get hashCode {
		const int prime = 31;
		int result = super.hashCode;
		result = prime * result + ((id == null) ? 0 : id.hashCode);
		return result;
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'date': DateFormat('yyyy-MM-dd hh:mm:ss').format(date!),
      'client': client!.toMap(),
    };
  }

  static PurchaseOrder fromMap(Map<String, dynamic> map) {
    return PurchaseOrder(
      id: map['id'],
      date: DateTime.parse(map['date']),
      client: Client.fromMap(map['client']),
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