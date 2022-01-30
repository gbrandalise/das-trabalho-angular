import 'package:das_angular_mobile/menu/menu.component.dart';
import 'package:flutter/material.dart';

class PurchaseOrderPage extends StatelessWidget {
  
  const PurchaseOrderPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Purchase Order'),
      ),
      drawer: const Menu()
    );
  }
}