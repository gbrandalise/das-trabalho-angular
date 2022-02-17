
import 'package:das_angular_mobile/client/models/user.dart';
import 'package:das_angular_mobile/client/provider/users.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class AddClint extends StatelessWidget {
  final _formKey = GlobalKey<FormState>();
  final Map<String, String> _formData = {};

  void _editFormData(User user){
    if(user != null){
      _formData['id'] = (user.id ?? 0) as String;
      _formData['firstName']= user.firstName;
      _formData['lastName']= user.lastName;
      _formData['cpf']= user.cpf;
    }
  }

  @override
  Widget build(BuildContext context) {
    // final User user = ModalRoute.of(context)?.settings.arguments as User; // arrumar esta efitando no editar user
    //   _editFormData(user);

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
                        id: (_formData['id'] ?? 0 ) as int ,
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
                    initialValue: _formData['firstName'],
                    decoration: InputDecoration(labelText: 'Primeiro Nome'),
                    onSaved: (value) => _formData['firstName'] = value!,
                  ),
                  TextFormField(
                    initialValue: _formData['lastName'],
                    decoration: InputDecoration(labelText: 'Ultimo Nome'),
                    onSaved: (value) => _formData['lastName'] = value!,
                  ),
                  TextFormField(
                    initialValue: _formData['cpf'],
                    decoration: InputDecoration(labelText: 'CPF'),
                    onSaved: (value) => _formData['cpf'] = value!,
                  ),
                ],
              ),
            )));
  }
}
