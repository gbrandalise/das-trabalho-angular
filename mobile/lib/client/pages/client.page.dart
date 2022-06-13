import 'package:cpf_cnpj_validator/cpf_validator.dart';
import 'package:das_angular_mobile/common/exception/http-exception.dart';
import 'package:das_angular_mobile/common/widgets/page-title.widget.dart';
import 'package:das_angular_mobile/menu/menu.component.dart';
import 'package:das_angular_mobile/routes/app_routes.dart';
import 'package:flutter/material.dart';

import '../client.model.dart';
import '../services/client.services.dart';

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
     _getArguments();
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
                    validator: (value) => _validateNotEmtpty(value, 'Nome'),
                    decoration: const InputDecoration(
                      enabled: true,
                      labelText: 'Nome',
                    ),
                  ),
                  TextFormField(
                    controller: _lastNameController,
                    validator: (value) => _validateNotEmtpty(value, 'Sobrenome'),
                    decoration: const InputDecoration(
                      enabled: true,
                      labelText: 'Sobrenome',
                    ),
                  ),
                  TextFormField(
                    controller: _cpfController,
                    validator: (value) => _validateCpf(value),
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

  String? _validateNotEmtpty(String? value, String field) {
    if (value == null 
        || value == '') {
      return '$field inválido!';
    }
    return null;
  }

  String? _validateCpf(String? value) {
    if (_validateNotEmtpty(value, 'CPF') == null) {
      if (!CPFValidator.isValid(value)) {
        return 'CPF inválido!';
      }
    }
    return null;
  }

  void _save() async {
    if (_formKeyClient.currentState!.validate()) {
      try {
        _client.firstName = _firstNameController.text;
        _client.lastName = _lastNameController.text;
        _client.cpf = _cpfController.text;
        _client = await _clientService.save(_client, context);
        _redirectList();
      } on HttpException catch(e) {
        if (e.statusCode == 412) {
          showDialog(
            context: context,
            builder: (BuildContext context) {
              return AlertDialog(
                title: const Text("Cliente"),
                content: const Text("CPF já cadastrado."),
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
      } catch (e) {
        _handleError();
      }
    }
  }

  void _handleError() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text("Cliente"),
          content: const Text("Ops, houve um erro. Tente novamente mais tarde."),
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

  void _redirectList() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Cliente'),
          content: const Text('Cliente salvo com sucesso'),
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

  void _getArguments() {
    if (_client.id == null) {
      final args = ModalRoute.of(context)!.settings.arguments;
      if (args != null) {
        setState(() {
          _client = args as Client;
          _firstNameController.text = _client.firstName!;
          _lastNameController.text = _client.lastName!;
          _cpfController.text = _client.cpf!;
        });
      }
    }
  }
}
