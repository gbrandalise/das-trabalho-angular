import 'dart:convert';

import 'package:das_angular_mobile/client/client.model.dart';
import 'package:das_angular_mobile/common/environment.dart';

import 'package:http/http.dart' as http;

class ClientService {
  static const String CLIENT_URL = '/clients';

  Future<List<Client>> findAll() async {
    http.Response response = await http.get(
        Uri.http(Environment.API_URL, CLIENT_URL),
        headers: Environment.HEADERS);
    if (response.statusCode == 200) {
      return Client.fromJsonList(response.body);
    } else {
      throw Exception('Error code: ${response.statusCode}');
    }
  }

  Future<Client> save(Client client) async{
    if(client.id != null){
      return update(client);
    }
    return insert(client);
  }

  Future<Client> insert(Client client) async {
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
        Uri.http(Environment.API_URL, '${CLIENT_URL}/${client.id}'),
        headers: Environment.HEADERS,
        body: client.toJson());
    if (response.statusCode == 200) {
      return client;
    } else {
      throw Exception('Error code: ${response.statusCode}');
    }
  }

  Future<void> delete(int id) async {
    final http.Response response = await http.delete(
        Uri.http(Environment.API_URL, '${CLIENT_URL}/$id'),
        headers: Environment.HEADERS);
    if (response.statusCode == 200) {
      Client.fromJson(response.body);
    } else {
      throw Exception('Error code: ${response.statusCode}');
    }
  }
}
