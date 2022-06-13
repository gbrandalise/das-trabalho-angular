import 'package:das_angular_mobile/client/client.model.dart';
import 'package:das_angular_mobile/client/services/client.services.dart';
import 'package:das_angular_mobile/common/services/loading.service.dart';
import 'package:das_angular_mobile/common/widgets/list-item-card.widget.dart';
import 'package:das_angular_mobile/common/widgets/page-title.widget.dart';
import 'package:das_angular_mobile/menu/menu.component.dart';
import 'package:das_angular_mobile/routes/app_routes.dart';
import 'package:flutter/material.dart';

class ClientsPage extends StatefulWidget {
  const ClientsPage({Key? key}) : super(key: key);
  @override
  State<StatefulWidget> createState() => _ClientsPageState();
}

class _ClientsPageState extends State<ClientsPage> {

  final ClientService clientService = ClientService();
  List<Client> _list = [];

  @override
  void initState() {
    super.initState();
    _findAll();
  }

  _findAll() async {
    LoadingService.show(context);
    List<Client> result = await clientService.findAll();
    setState(() {
      _list = result;
    });
    LoadingService.hide(context);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Cliente'),
      ),
      drawer: const Menu(),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          await Navigator.pushNamed(context, AppRoutes.CLIENT_REGISTER);
          _findAll();
        },
        child: const Icon(Icons.add),
      ),
      body: Column(children: [
        const PageTitle('Lista de Clientes'),
        Expanded(
          child: ListView.builder(
            padding: const EdgeInsetsDirectional.fromSTEB(0, 10, 0, 100),
            itemCount: _list.length,
            itemBuilder: (context, index) {
              final Client client = _list[index];
              return ListItemCard(
                {
                  'Primeiro Nome': client.firstName.toString(),
                  'Ultimo Nome': client.lastName.toString(),
                  'CPF': client.cpf.toString(),
                },
                onEdit: () => _edit(client),
                onDelete: () {
                  _confirmDelete(client);
                },
              );
            }
          ),
        ),
      ]),
    );
  }

  _confirmDelete(Client client) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text("Cliente"),
          content: const Text("Deseja realmente excluir o cliente?"),
          actions: [
            TextButton(
              child: const Text("Sim"),
              onPressed: () {
                Navigator.pop(context);
                _delete(client);
              },
            ),
            TextButton(
              child: const Text("Não"),
              onPressed: () {
                Navigator.pop(context);
              },
            )
          ],
        );
      },
    );
  }

  _delete(Client client) async {
    try {
      LoadingService.show(context);
      await clientService.delete(client.id!);
      LoadingService.hide(context);
      _findAll();
    } catch(e) {
      LoadingService.hide(context);
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: const Text('Cliente'),
            content: const Text('O cliente possui pedidos, remova-os antes de excluí-lo.'),
            actions: [
              TextButton(
                  child: const Text("OK"),
                  onPressed: () {
                    Navigator.popUntil(
                        context, ModalRoute.withName(AppRoutes.CLIENT));
                  })
            ],
          );
      });
    }
  }

  _edit(Client client) async {
    await Navigator.pushNamed(context, AppRoutes.CLIENT_REGISTER,
        arguments: client);
    _findAll();
  }
}
