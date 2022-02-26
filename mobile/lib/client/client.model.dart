import 'dart:convert';

class Client {
  int? id;
  String? cpf;
  String? firstName;
  String? lastName;

  Client(this.id, this.cpf, this.firstName, this.lastName);

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'cpf': cpf,
      'firstName': firstName,
      'lastName': lastName
    };
  }

  static Client fromMap(Map<String, dynamic> map) {
    return Client(
      map['id'],
      map['cpf'],
      map['firstName'],
      map['lastName'],
    );
  }
  
  static List<Client> fromMaps(List<Map<String, dynamic>> maps) {
    return List.generate(maps.length, (i) {
      return Client.fromMap(maps[i]);
    });
  }

  static Client fromJson(String json) => Client.fromMap(jsonDecode(json));

  static List<Client> fromJsonList(String json) {
    final parsed = jsonDecode(json).cast<Map<String, dynamic>>();
    return parsed.map<Client>((map) => Client.fromMap(map)).toList();
  }

  String toJson() => jsonEncode(toMap());


}