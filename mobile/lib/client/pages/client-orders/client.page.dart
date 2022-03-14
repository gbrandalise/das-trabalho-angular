import 'dart:ffi';

import 'package:das_angular_mobile/client/client.model.dart';
import 'package:das_angular_mobile/client/services/client.services.dart';
import 'package:das_angular_mobile/common/services/loading.service.dart';
import 'package:das_angular_mobile/routes/app_routes.dart';
import 'package:flutter/material.dart';

class ClientPage extends StatefulWidget {
  @override
  State<ClientPage> createState() => _ClientPageState();
}

class _ClientPageState extends State<ClientPage> {
  final _formKeyClient = GlobalKey<FormState>();
  final _cpfController = TextEditingController();
  final _firstNameController = TextEditingController();
  final _lastNameController = TextEditingController();

  final _clientService = ClientService();

  Client _client = Client();

  @override
  void dispose(){
    _cpfController.dispose();
    _firstNameController.dispose();
    _lastNameController.dispose();
    super.dispose();
  }
  @override
  Widget build(BuildContext context) {
    _getArguments();
    return Scaffold(
        appBar: AppBar(
          title: Text("Cadastro de Cliente"),
          actions: [
            IconButton(
              icon: Icon(Icons.save),
              onPressed: () {
                _save();
              },
            )
          ],
        ),
        body: Padding(
            padding: EdgeInsets.all(15),
            child: Form(
              key: _formKeyClient,
              child: Column(
                children: [
                  TextFormField(
                   // validator: (value) => _validateForm(value),
                    autofocus: true,
                    controller: _firstNameController,
                    decoration: InputDecoration(labelText: 'Primeiro Nome'),
                    enabled: true,
                  ),
                  TextFormField(
                    //validator: (value) => _validateForm(value),
                    controller: _lastNameController,
                    decoration: InputDecoration(labelText: 'Ultimo Nome'),
                    enabled: true,
                  ),
                  TextFormField(
                   // validator: (value) => _validateForm(value),
                    keyboardType: TextInputType.number,
                    controller: _cpfController,
                    decoration: InputDecoration(labelText: 'CPF'),
                    enabled: true,
                  ),
                ],
              ),
            )));
  }
//_formKeyClient.currentState!.validate() &&
  void _save() async{
    if(_validateDescription()){
      try{
        _client.firstName = _firstNameController.text;
        _client.lastName = _lastNameController.text;
        _client.cpf = _cpfController.text;
        _client = await _clientService.save(_client);
      }finally{
        LoadingService.hide(context);
      }
      _redirectList();
    }
  }

  void _redirectList() {
    showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: const Text('Cliente'),
            content: const Text('Cliente salvo com sucesso!'),
            actions: [
              TextButton(
                  child: const Text("OK"),
                  onPressed: () {
                    Navigator.popUntil(
                        context, ModalRoute.
                    withName(AppRoutes.CLIENT));
                  })
            ],
          );
        });
  }

//   String? _validateForm(String? value) {
//     if(value == null){
//       return 'Campo nao pode ser vazio!';
//     }
//     return null;
// }

  bool _validateDescription(){
    if(_formKeyClient.currentState!.validate() && _firstNameController.text.isEmpty && _lastNameController.text.isEmpty && _cpfController.text.isEmpty){
      showDialog(
          context: context, builder: (BuildContext context){
            return AlertDialog(
              title: const Text("Cliente"),
              content: const Text("Por favor os campos em vermelho nao podem ser fazios"),
              actions: [
                TextButton(onPressed: () {
                  Navigator.pop(context);
                }, child: const Text("OK")
                )
              ],
            );
      }
      );
      return false;
    }
    return true;
  }
  void _getArguments(){
   if(_client.id == null){
     final args = ModalRoute.of(context)!.settings.arguments;
     if(args != null ){
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
