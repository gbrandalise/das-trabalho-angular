import 'package:das_angular_mobile/client/client.model.dart';
import 'package:das_angular_mobile/common/environment.dart';
import 'package:das_angular_mobile/common/widgets/list-item-card.widget.dart';

import 'package:http/http.dart' as http;

class ClientService {
  static const String CLIENT_URL = '/clients';
  //static const String CLIENT_URL_DELETE = '/clients/$id';

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

  // Future<Client> remove(int id) async {
  //   final http.Response response = await http.delete(
  //       Uri.http(Environment.API_URL, '/clients/$id'),
  //       headers: Environment.HEADERS);
  //   if (response.statusCode == 200) {
  //     Client.fromJson(response.body);
  //   } else {
  //     throw Exception('Error code: ${response.statusCode}');
  //   }
  // }
}
