import 'package:das_angular_mobile/client/client.model.dart';
import 'package:das_angular_mobile/client/pages/client-orders/AddClint.dart';
import 'package:das_angular_mobile/client/services/client.services.dart';
import 'package:das_angular_mobile/menu/menu.component.dart';
import 'package:das_angular_mobile/routes/app_routes.dart';
import 'package:flutter/material.dart';

class ClientPage extends StatefulWidget {
  const ClientPage({Key? key}) : super(key: key);
  @override
  State<StatefulWidget> createState() => _ClientPageState();
}

class _ClientPageState extends State<ClientPage> {
  final ClientService clientService = ClientService();
  List<Client> _list = [];

  @override
  void initState() {
    super.initState();
    _findAll();
  }

  _findAll() async {
    List<Client> result = await clientService.findAll();
    setState(() {
      _list = result;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Lista de Clientes'),
        actions: <Widget>[
          IconButton(
              onPressed: () {
                Navigator.push(context,
                    MaterialPageRoute(builder: (context) => AddClint()));
              },
              icon: Icon(Icons.person_add))
        ],
      ),
      body: ListView.builder(
          itemCount: _list.length,
          itemBuilder: (context, index) {
            final Client client = _list[index];
            return ListTile(
                title:
                    Text("Id: ${client.id}\nPrimeiro Nome:${client.firstName}"),
                subtitle:
                    Text("Ultimo Nome:${client.lastName}\nCPF:${client.cpf}"),
                trailing: Container(
                  width: 100,
                  child: Row(
                    children: <Widget>[
                      IconButton(
                          onPressed: () {
                            Navigator.of(context).pushNamed(
                                AppRoutes.USER_ADDCLINT,
                                arguments: client.id);
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
                                            onPressed: () =>
                                                Navigator.of(context)
                                                    .pop(false),
                                            child: Text("Não")),
                                        TextButton(
                                            onPressed: () =>
                                                Navigator.of(context).pop(true),
                                            child: Text("Sim"))
                                      ],
                                    )).then((value) {
                              if (value) {
                                //Provider.of<Client>(context, listen: false)
                                //.remove();
                              }
                            });
                          },
                          icon: Icon(Icons.delete_forever),
                          color: Colors.red)
                    ],
                  ),
                ));
          }),
      drawer: const Menu(),
    );
  }
}
