import 'package:flutter/material.dart';

class Menu extends StatelessWidget {

  const Menu({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: [
            DrawerHeader(
              child: Column(
                children: const [
                  Text('Trabalho do Razer'),
                  Image(
                    image: AssetImage('assets/images/razer-logo.png'),
                    width: 120,
                  )
                ]
              )
            ),
            ListTile(
              title: const Text('Cliente'),
              onTap: () {
                Navigator.pop(context);
                Navigator.pushNamed(context, '/client');
              },
            ),
            ListTile(
              title: const Text('Produto'),
              onTap: () {
                Navigator.pop(context);
                Navigator.pushNamed(context, '/product');
              },
            ),
            ListTile(
              title: const Text('Pedido'),
              onTap: () {
                Navigator.pop(context);
                Navigator.pushNamed(context, '/purchase-order');
              },
            ),
          ],
        ),
      );
  }

}