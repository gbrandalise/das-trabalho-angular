import 'package:das_angular_mobile/client/models/user.dart';
import 'package:das_angular_mobile/client/views/AddClint.dart';
import 'package:flutter/material.dart';

class UserTile extends StatelessWidget {
  final User user;

  const UserTile(this.user);

  @override
  Widget build(BuildContext context) {
    return ListTile(
      title: Text("Id: ${user.id}\nPrimeiro Nome:${user.firstName}"),
      subtitle: Text("Ultimo Nome:${user.lastName}\nCPF:${user.cpf}"),

      trailing: Container(
        width: 100,
      child: Row(
        children: <Widget>[IconButton(onPressed: () {
          Navigator.push(context, MaterialPageRoute(builder: (context) => AddClint()));
        }, icon: Icon(Icons.edit),
        color:Colors.blue),
          IconButton(onPressed: () {}, icon: Icon(Icons.delete_forever),
          color: Colors.red)],
      ),
      )
    );
  }
}
