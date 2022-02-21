
import 'package:das_angular_mobile/client/provider/users.dart';
import 'package:das_angular_mobile/client/views/AddClint.dart';
import 'package:das_angular_mobile/client/views/client.page.dart';
import 'package:das_angular_mobile/home/home.page.dart';
import 'package:das_angular_mobile/product/pages/product.page.dart';
import 'package:das_angular_mobile/purchase-order/pages/purchase-orders/purchase-orders.page.dart';
import 'package:das_angular_mobile/routes/app_routes.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';



void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
     return MultiProvider(providers: [
        ChangeNotifierProvider(create: (ctx) => Users(),),
     ],
    child: MaterialApp(
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
      },
    ),
    );
  }
}
