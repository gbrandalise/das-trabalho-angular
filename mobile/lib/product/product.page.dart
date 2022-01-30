import 'package:das_angular_mobile/menu/menu.component.dart';
import 'package:flutter/material.dart';

class ProductPage extends StatelessWidget {
  
  const ProductPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Product'),
      ),
      drawer: const Menu()
    );
  }
}