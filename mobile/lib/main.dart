import 'package:das_angular_mobile/client/client.page.dart';
import 'package:das_angular_mobile/home/home.page.dart';
import 'package:das_angular_mobile/product/product.page.dart';
import 'package:das_angular_mobile/purchase-order/pages/purchase-orders/purchase-orders.page.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Trabalho Mobile Razer',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      initialRoute: '/',
      routes: {
        '/': (context) => const HomePage(),
        '/client': (context) => const ClientPage(),
        '/product': (context) => const ProductPage(),
        '/purchase-order': (context) => const PurchaseOrdersPage(),
      },
    );
  }
}

