import 'package:das_angular_mobile/client/models/user.dart';
import 'package:das_angular_mobile/client/provider/users.dart';
import 'package:das_angular_mobile/client/views/AddClint.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../routes/app_routes.dart';

class UserTile extends StatefulWidget {
  final User user;

  const UserTile(this.user);

  @override
  State<UserTile> createState() => _UserTileState();
}

class _UserTileState extends State<UserTile> {
  @override
  Widget build(BuildContext context) {
    return ListTile(
        title: Text("Id: ${widget.user.id}\nPrimeiro Nome:${widget.user.firstName}"),
        subtitle: Text("Ultimo Nome:${widget.user.lastName}\nCPF:${widget.user.cpf}"),
        trailing: Container(
          width: 100,
          child: Row(
            children: <Widget>[
              IconButton(
                  onPressed: () {
                    Navigator.of(context).pushNamed(AppRoutes.USER_ADDCLINT, arguments: widget.user);
                  },
                  icon: Icon(Icons.edit),
                  color: Colors.blue),
              IconButton(
                  onPressed: () {
                    showDialog(
                        context: context,
                        builder: (ctx) => AlertDialog(
                              title: Text("Deseja excluir usuario ?"),
                              actions: <Widget>[
                                TextButton(
                                    onPressed: () =>  Navigator.of(context).pop(false), child: Text("Não")),
                                TextButton(
                                    onPressed: () =>  Navigator.of(context).pop(true),child: Text("Sim"))
                              ],
                            )
                    ).then((value){
                      if(value){
                        Provider.of<Users>(context, listen: false)
                            .remove(widget.user);
                      }
                    });
                  },
                  icon: Icon(Icons.delete_forever),
                  color: Colors.red)
            ],
          ),
        ));
  }
}
