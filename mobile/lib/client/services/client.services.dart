import 'dart:convert';

import 'package:das_angular_mobile/client/client.model.dart';
import 'package:das_angular_mobile/common/environment.dart';

import 'package:http/http.dart' as http;

class ClientService {
  // ignore: constant_identifier_names
  static const String CLIENT_URL = '/clients';

  Future<List<Client>> findAll() async {
    http.Response response = await http.get(
        Uri.http(Environment.API_URL, CLIENT_URL),
        headers: Environment.HEADERS);
    if (response.statusCode == 200) {
      return Client.fromJsonList(utf8.decode(response.bodyBytes));
    } else {
      throw Exception('Error code: ${response.statusCode}');
    }
  }

  Future<Client> save(Client client) async {
    if (client.id != null) {
      return update(client);
    }
    return create(client);
  }

  Future<Client> create(Client client) async {
    http.Response response = await http.post(
        Uri.http(Environment.API_URL, CLIENT_URL),
        headers: Environment.HEADERS,
        body: client.toJson());
    if (response.statusCode == 201) {
      return Client.fromJson(utf8.decode(response.bodyBytes));
    } else {
      throw Exception('Error code: ${response.statusCode}');
    }
  }

  Future<Client> update(Client client) async {
    http.Response response = await http.put(
        Uri.http(Environment.API_URL, '$CLIENT_URL/${client.id}'),
        headers: Environment.HEADERS,
        body: client.toJson());
    if (response.statusCode == 200) {
      return Client.fromJson(utf8.decode(response.bodyBytes));
    } else {
      throw Exception('Error code: ${response.statusCode}');
    }
  }

  Future<void> delete(int id) async {
    http.Response response = await http.delete(
      Uri.https(Environment.API_URL, '$CLIENT_URL/$id'),
      headers: Environment.HEADERS,
    );
    if (response.statusCode != 200) {
      throw Exception('Error code: ${response.statusCode}');
    }
  }
}
