import 'package:das_angular_mobile/common/services/loading.service.dart';
import 'package:das_angular_mobile/menu/menu.component.dart';
import 'package:das_angular_mobile/common/widgets/page-title.widget.dart';
import 'package:cpf_cnpj_validator/cpf_validator.dart';
import 'package:flutter/material.dart';

import '../services/client.services.dart';
import '../client.model.dart';

class ClientPage extends StatefulWidget {

  const ClientPage({Key? key}) : super(key: key);

  @override
  State<ClientPage> createState() => _ClientPageState();

}

class _ClientPageState extends State<ClientPage> {
  final _formKeyClient = GlobalKey<FormState>();
  final _firstNameController = TextEditingController();
  final _lastNameController = TextEditingController();
  final _cpfController = TextEditingController();

  final ClientService _clientService = ClientService();

  Client _client = Client();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Cadastro de Cliente"),
        actions: [
          IconButton(
            icon: const Icon(Icons.save),
            iconSize: 40,
            onPressed: () {
              _save();
            },
          )
        ],
      ),
      drawer: const Menu(),
      body: Column(
        children: [
          const PageTitle('Cadastrar Cliente'),
          Form(
            key: _formKeyClient,
            child: Container(
              margin: const EdgeInsetsDirectional.fromSTEB(10, 20, 10, 0),
              child: Column(
                children: [
                  TextFormField(
                    controller: _firstNameController,
                    decoration: const InputDecoration(
                      enabled: true,
                      labelText: 'Nome',
                    ),
                  ),
                  TextFormField(
                    controller: _lastNameController,
                    decoration: const InputDecoration(
                      enabled: true,
                      labelText: 'Sobrenome',
                    ),
                  ),
                  TextFormField(
                    controller: _cpfController,
                    decoration: const InputDecoration(
                      enabled: true,
                      labelText: 'CPF',
                    ),
                  ),
                ],
              ),
            )
          )
        ]
      )
    );
  }

  void _save() async {
    if(_isFormValid()) {
      try {
        _client.firstName = _firstNameController.text;
        _client.lastName = _lastNameController.text;
        _client.cpf = _cpfController.text;
        _client = await _clientService.save(_client);
      } finally {
        LoadingService.hide(context);
      }

    } else {
      _handleError();
    }
  }

  bool _isFormValid() {
    return CPFValidator.isValid(_cpfController.text) &&
      !_firstNameController.text.isEmpty &&
      !_lastNameController.text.isEmpty;
  }

  void _handleError () {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text("Produto"),
          content: const Text("Ops, encontramos um erro no seu formulário."),
          actions: [
            TextButton(
              child: const Text("OK"),
              onPressed: () {
                Navigator.pop(context);
              },
            ),
          ],
        );
      },
    );
  }
}
