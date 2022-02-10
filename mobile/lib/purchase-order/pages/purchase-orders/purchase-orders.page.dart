import 'package:das_angular_mobile/common/widgets/page-title.widget.dart';
import 'package:das_angular_mobile/menu/menu.component.dart';
import 'package:flutter/material.dart';

class PurchaseOrdersPage extends StatelessWidget {
  
  final _formKey = GlobalKey<FormState>();

  PurchaseOrdersPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Purchase Order'),
      ),
      drawer: const Menu(),
      body: Column(
        children: [
          const PageTitle('Lista de Pedidos'),
          Form(            
            key: _formKey,
            child: Container(
              margin: const EdgeInsetsDirectional.fromSTEB(10, 20, 10, 0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Expanded(
                    child: TextFormField(
                      decoration: const InputDecoration(
                        hintText: 'CPF do Cliente'
                      ),
                    ),
                  ),
                  ElevatedButton.icon(
                    onPressed: () {}, 
                    icon: const Icon(Icons.search),
                    label: const Text('Filtrar'),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}