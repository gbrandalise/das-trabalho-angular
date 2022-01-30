import 'package:das_angular_mobile/menu/menu.component.dart';
import 'package:flutter/material.dart';

class ClientPage extends StatelessWidget {
  
  const ClientPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Client'),
      ),
      drawer: const Menu()
    );
  }
}