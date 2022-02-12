import 'package:das_angular_mobile/client/components/user_tile.dart';
import 'package:das_angular_mobile/client/models/user.dart';
import 'package:das_angular_mobile/client/provider/users.dart';
import 'package:das_angular_mobile/client/views/AddClint.dart';
import 'package:das_angular_mobile/menu/menu.component.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ClientPage extends StatefulWidget {
  static const String routeName= 'list';
  State<StatefulWidget> createState()=> _ClientPageState();
}

class _ClientPageState extends State<ClientPage>{

  Widget build(BuildContext context) {
    final Users users = Provider.of(context);

    return Scaffold(
        appBar: AppBar(
          title: const Text('Lista de Clientes'),
          actions: <Widget>[
           IconButton(onPressed: () {
              Navigator.push(context, MaterialPageRoute(builder: (context) => AddClint()));
              }, icon: Icon(Icons.person_add))
          ],
        ),
        body: ListView.builder(itemBuilder: (ctx, i) => UserTile(users.byIndex(i)), itemCount: users.count,),
        drawer: const Menu(),
    );
  }
}

