
import 'dart:ffi';

import 'package:das_angular_mobile/client/models/user.dart';
import 'package:das_angular_mobile/client/provider/users.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class AddClint extends StatefulWidget {
  @override
  State<AddClint> createState() => _AddClintState();
  final _formKey = GlobalKey<FormState>();

  final Map<String, String> _formData = {};

  void _editFormData(User user){
    if(user != null) {
      _formData['id'] = user.id!.toString();
      _formData['firstName']= user.firstName;
      _formData['lastName']= user.lastName;
      _formData['cpf']= user.cpf;
    }
  }
}

class _AddClintState extends State<AddClint> {


  @override
  Widget build(BuildContext context) {
      final User? user = ModalRoute.of(context)!.settings.arguments as User?;
      if(user != null) {
        widget._editFormData(user);
      }
    return Scaffold(
        appBar: AppBar(
          title: Text("Cadastro de Cliente"),
          actions: [
            IconButton(
                onPressed: () {
                  final isValidate = widget._formKey.currentState?.validate();
                  if ( isValidate != null && widget._formData['id'] != null) {
                    widget._formKey.currentState?.save();
                    Provider.of<Users>(context, listen: false).put(
                      User(
                          id: int.parse(widget._formData['id']!),
                          firstName: widget._formData['firstName']!,
                          lastName: widget._formData['lastName']!,
                          cpf: widget._formData['cpf']!
                      ),
                    );
                    Navigator.of(context).pop();
                  }else{
                    widget._formKey.currentState?.save();
                    Provider.of<Users>(context, listen: false).put(
                      User(
                          id: (widget._formData['id'] ?? 0 ) as int,
                          firstName: widget._formData['firstName']!,
                          lastName: widget._formData['lastName']!,
                          cpf: widget._formData['cpf']!
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
              key: widget._formKey,
              child: Column(
                children: <Widget>[
                  TextFormField(
                    initialValue:widget._formData['firstName'],
                    decoration: InputDecoration(labelText: 'Primeiro Nome'),
                  validator: (value) {
                    if (value!.isEmpty) {
                      return 'Primeiro Nome não pode ser vazio';
                    }
                    return null;
                  },
                    onSaved: (value) => widget._formData['firstName'] = value!,
                  ),
                  TextFormField(
                    initialValue: widget._formData['lastName'],
                    decoration: InputDecoration(labelText: 'Ultimo Nome'),
                    validator: (value) {
                      if (value!.isEmpty) {
                        return 'Ultimo Nome não pode ser vazio';
                      }
                      return null;
                    },
                    onSaved: (value) => widget._formData['lastName'] = value!,
                  ),
                  TextFormField(
                    initialValue: widget._formData['cpf'],
                    validator: (value) {
                      if (value!.isEmpty) {
                        return 'CPF não pode ser vazio';
                      }
                      return null;
                    },
                    decoration: InputDecoration(labelText: 'CPF'),
                    onSaved: (value) => widget._formData['cpf'] = value!,
                  ),
                ],
              ),
            )));
  }
}
