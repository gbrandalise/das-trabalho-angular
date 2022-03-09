import 'dart:ffi';

import 'package:das_angular_mobile/client/client.model.dart';
import 'package:flutter/material.dart';

class AddClint extends StatefulWidget {
  @override
  State<AddClint> createState() => _AddClintState();
}

class _AddClintState extends State<AddClint> {
  final _formKey = GlobalKey<FormState>();
  List<Client> _list = [];

  void _salvar() async {
    
  }

  @override
  Widget build(BuildContext context) {
    // final User? user = ModalRoute.of(context)!.settings.arguments as User?;
    // if(user != null) {
    //   widget._editFormData(user);
    // }
    return Scaffold(
        appBar: AppBar(
          title: Text("Cadastro de Cliente"),
          actions: [
            IconButton(
              icon: Icon(Icons.save),
              onPressed: () {


                //final isValidate = widget._formKey.currentState?.validate();
                //if ( isValidate != null && widget._formData['id'] != null) {
                // widget._formKey.currentState?.save();
                // Provider.of<Client>(context, listen: false).put(
                //   User(
                //       id: int.parse(widget._formData['id']!),
                //       firstName: widget._formData['firstName']!,
                //       lastName: widget._formData['lastName']!,
                //       cpf: widget._formData['cpf']!
                //   ),
                // );
                //   Navigator.of(context).pop();
                // }else{
                //   widget._formKey.currentState?.save();
                //   Provider.of<Users>(context, listen: false).put(
                //     User(
                //         id: (widget._formData['id'] ?? 0 ) as int,
                //         firstName: widget._formData['firstName']!,
                //         lastName: widget._formData['lastName']!,
                //         cpf: widget._formData['cpf']!
                //     ),
                //   );
                //   Navigator.of(context).pop();
                // }
              },
            )
          ],
        ),
        body: Padding(
            padding: EdgeInsets.all(15),
            child: Form(
              key: _formKey,
              child: Column(
                children: <Widget>[
                  // TextFormField(
                  //   initialValue: _formData['firstName'],
                  //   decoration: InputDecoration(labelText: 'Primeiro Nome'),
                  //   validator: (value) {
                  //     if (value!.isEmpty) {
                  //       return 'Primeiro Nome não pode ser vazio';
                  //     }
                  //     return null;
                  //   },
                  //   onSaved: (value) => _formData['firstName'] = value!,
                  // ),
                  // TextFormField(
                  //   initialValue: _formData['lastName'],
                  //   decoration: InputDecoration(labelText: 'Ultimo Nome'),
                  //   validator: (value) {
                  //     if (value!.isEmpty) {
                  //       return 'Ultimo Nome não pode ser vazio';
                  //     }
                  //     return null;
                  //   },
                  //   onSaved: (value) => _formData['lastName'] = value!,
                  // ),
                  // TextFormField(
                  //   initialValue: _formData['cpf'],
                  //   validator: (value) {
                  //     if (value!.isEmpty) {
                  //       return 'CPF não pode ser vazio';
                  //     }
                  //     return null;
                  //   },
                  //   decoration: InputDecoration(labelText: 'CPF'),
                  //   onSaved: (value) => _formData['cpf'] = value!,
                 // ),
                ],
              ),
            )));
  }
}
