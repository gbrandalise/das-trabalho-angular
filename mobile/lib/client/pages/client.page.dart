
import 'package:das_angular_mobile/menu/menu.component.dart';
import 'package:flutter/material.dart';

class ClientPage extends StatefulWidget {

  const ClientPage({Key? key}) : super(key: key);

  @override
  State<ClientPage> createState() => _ClientPageState();

}

class _ClientPageState extends State<ClientPage> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Cadastro de Cliente"),
        actions: [
          IconButton(
            icon: const Icon(Icons.save),
            onPressed: () {},
          )
        ],
      ),
      drawer: const Menu(),
    );
  }
}
