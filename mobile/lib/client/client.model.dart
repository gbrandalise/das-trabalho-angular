import 'dart:convert';

class Client {
  int? id;
  String? cpf;
  String? firstName;
  String? lastName;

  Client({this.id, this.cpf, this.firstName, this.lastName});

  @override
  bool operator == (dynamic other) =>
      other != null && other is Client && id == other.id;

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
      'cpf': cpf,
      'firstName': firstName,
      'lastName': lastName
    };
  }

  static Client fromMap(Map<String, dynamic> map) {
    return Client(
      id: map['id'],
      cpf: map['cpf'],
      firstName: map['firstName'],
      lastName: map['lastName'],
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