import 'package:das_angular_mobile/menu/menu.component.dart';
import 'package:das_angular_mobile/common/widgets/page-title.widget.dart';
import 'package:flutter/material.dart';

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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Cadastro de Cliente"),
        actions: [
          IconButton(
            icon: const Icon(Icons.save),
            iconSize: 40,
            onPressed: () {},
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
  bool _isFormValid() {
    return CPFValidator.isValid(_cpfController.text) &&
      !_firstNameController.text.isEmpty &&
      !_lastNameController.text.isEmpty;
  }
}
