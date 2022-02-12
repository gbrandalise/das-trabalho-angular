import 'package:das_angular_mobile/client/models/user.dart';
import 'package:das_angular_mobile/client/provider/users.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class AddClint extends StatelessWidget {
  final _formKey = GlobalKey<FormState>();
  final Map<String, String> _formData = {};

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("Cadastro de Cliente"),
          actions: [
            IconButton(
                onPressed: () {
                  final isValid = _formKey.currentState?.validate();
                  if (isValid!) {
                    _formKey.currentState?.save();
                    Provider.of<Users>(context, listen: false).put(
                      User(
                        //id: _formData['id'], aqui nao precisar, preciso achar um jeito de validar
                        firstName: _formData['firstName']!,
                        lastName: _formData['lastName']!,
                        cpf: _formData['cpf']!
                      ),
                    );
                    Navigator.of(context).pop();
                  }
                },
                icon: Icon(Icons.save))
          ],
        ),
        body: Padding(
            padding: EdgeInsets.all(15),
            child: Form(
              key: _formKey,
              child: Column(
                children: <Widget>[
                  TextFormField(
                    decoration: InputDecoration(labelText: 'Primeiro Nome'),
                    onSaved: (value) => _formData['firstName'] = value!,
                  ),
                  TextFormField(
                    decoration: InputDecoration(labelText: 'Ultimo Nome'),
                    onSaved: (value) => _formData['lastName'] = value!,
                  ),
                  TextFormField(
                    decoration: InputDecoration(labelText: 'CPF'),
                    onSaved: (value) => _formData['cpf'] = value!,
                  ),
                ],
              ),
            )));
  }
}
