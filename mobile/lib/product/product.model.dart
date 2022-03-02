import 'dart:convert';

class Product {
  int? id;
  String? description;

  Product({this.id, this.description});

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'description': description,
    };
  }

  static Product fromMap(Map<String, dynamic> map) {
    return Product(
      id: map['id'], 
      description: map['description']
    );
  }

  static List<Product> fromJsonList(String json) {
    final parsed = jsonDecode(json).cast<Map<String, dynamic>>();
    return parsed.map<Product>((map) => Product.fromMap(map)).toList();
  }

  String toJson() => jsonEncode(toMap());
}
