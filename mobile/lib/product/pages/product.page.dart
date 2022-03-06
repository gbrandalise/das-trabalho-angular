import 'package:das_angular_mobile/common/services/loading.service.dart';
import 'package:das_angular_mobile/common/widgets/page-title.widget.dart';
import 'package:das_angular_mobile/routes/app_routes.dart';
import 'package:flutter/material.dart';

import '../../menu/menu.component.dart';
import '../product.model.dart';
import '../services/product.service.dart';

class ProductPage extends StatefulWidget {
  const ProductPage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _ProductPageState();
}

class _ProductPageState extends State<ProductPage> {
  final _formKeyProduct = GlobalKey<FormState>();
  final _descriptionController = TextEditingController();

  final ProductService _productsService = ProductService();

  Product _product = Product();

  @override
  void dispose() {
    _descriptionController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('Produto'),
          actions: [
            IconButton(
              onPressed: () {
                _save();
              },
              icon: const Icon(Icons.save),
              iconSize: 40,
            )
          ],
        ),
        drawer: const Menu(),
        body: Column(
          children: [
            const PageTitle('Cadastrar Produto'),
            Form(
              key: _formKeyProduct,
              child: Container(
                margin: const EdgeInsetsDirectional.fromSTEB(10, 20, 10, 0),
                child: Expanded(
                  child: Column(
                    children: [
                      TextFormField(
                        controller: _descriptionController,
                        decoration: const InputDecoration(
                          enabled: true,
                          labelText: 'Produto',
                        ),
                        style: const TextStyle(color: Colors.grey),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ],
        ));
  }

  void _save() async {
    if (_validateDescription()) {
      try {
        _product.description = _descriptionController.text;
        _product = await _productsService.save(_product);
      } finally {
        LoadingService.hide(context);
      }

      _redirectList();
    }
  }

  bool _validateDescription() {
    if (_formKeyProduct.currentState!.validate() &&
        _descriptionController.text.isEmpty) {
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: const Text("Produto"),
            content: const Text("Por favor adicionar descrição do produto."),
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
      return false;
    }
    return true;
  }

  void _redirectList() {
    showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: const Text('Produto'),
            content: const Text('Produto salvo com sucesso'),
            actions: [
              TextButton(
                  child: const Text("OK"),
                  onPressed: () {
                    Navigator.popUntil(
                        context, ModalRoute.withName(AppRoutes.PRODUCT));
                  })
            ],
          );
        });
  }
}
