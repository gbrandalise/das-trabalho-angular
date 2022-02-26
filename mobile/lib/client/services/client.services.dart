import 'package:das_angular_mobile/client/client.model.dart';
import 'package:das_angular_mobile/common/environment.dart';

import 'package:http/http.dart' as http;

class ClientService {
  // ignore: constant_identifier_names
  static const String CLIENT_URL = '/client';

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
}
