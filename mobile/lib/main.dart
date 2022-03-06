import 'package:das_angular_mobile/client/pages/client.page.dart';
import 'package:das_angular_mobile/client/pages/clients.page.dart';
import 'package:das_angular_mobile/home/home.page.dart';
import 'package:das_angular_mobile/product/pages/product.page.dart';
import 'package:das_angular_mobile/product/pages/products.page.dart';
import 'package:das_angular_mobile/purchase-order/pages/purchase-order.page.dart';
import 'package:das_angular_mobile/purchase-order/pages/purchase-orders.page.dart';
import 'package:das_angular_mobile/routes/app_routes.dart';
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
      initialRoute: AppRoutes.HOME,
      routes: {
        AppRoutes.HOME: (_) => const HomePage(),
        AppRoutes.CLIENT: (_) => const ClientsPage(),
        AppRoutes.CLIENT_REGISTER: (_) => const ClientPage(),
        AppRoutes.PRODUCT: (_) => const ProductsPage(),
        AppRoutes.PRODUCT_REGISTER: (_) => const ProductPage(),
        AppRoutes.PURCHASE_ORDER: (_) => const PurchaseOrdersPage(),
        AppRoutes.PURCHASE_ORDER_REGISTER: (_) => const PurchaseOrderPage(),
      },
    );
  }
}
