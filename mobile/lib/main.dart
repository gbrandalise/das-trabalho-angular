
import 'package:das_angular_mobile/client/pages/client-orders/AddClint.dart';
import 'package:das_angular_mobile/client/pages/client-orders/client.page.dart';
import 'package:das_angular_mobile/home/home.page.dart';
import 'package:das_angular_mobile/product/pages/product.page.dart';
import 'package:das_angular_mobile/purchase-order/pages/purchase-order/purchase-order.page.dart';
import 'package:das_angular_mobile/purchase-order/pages/purchase-orders/purchase-orders.page.dart';
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
      initialRoute: '/',
      routes: {
        AppRoutes.HOME: (_) =>  const HomePage(),
        AppRoutes.CLIENT: (_) => ClientPage(),
        AppRoutes.PRODUCT: (_) => const ProductPage(),
        AppRoutes.PURCHASE_ORDER: (_) => const PurchaseOrdersPage(),
        AppRoutes.USER_ADDCLINT: (_) => AddClint()
        '/purchase-order/register': (context) => const PurchaseOrderPage(),
      },
    );
  }
}
