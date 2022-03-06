import 'dart:convert';

class Product {
  int? id;
  String? description;

  Product({this.id, this.description});
  
  @override
  bool operator == (dynamic other) =>
      other != null && other is Product && id == other.id;

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
      'description': description,
    };
  }

  static Product fromMap(Map<String, dynamic> map) {
    return Product(id: map['id'], description: map['description']);
  }

  static List<Product> fromJsonList(String json) {
    final parsed = jsonDecode(json).cast<Map<String, dynamic>>();
    return parsed.map<Product>((map) => Product.fromMap(map)).toList();
  }

  String toJson() => jsonEncode(toMap());

  static Product fromJson(String json) => Product.fromMap(jsonDecode(json));

}
