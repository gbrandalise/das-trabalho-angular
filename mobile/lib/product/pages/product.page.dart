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
    _getArguments();
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
                child: Column(
                  children: [
                    TextFormField(
                      controller: _descriptionController,
                      validator: (value) => _validateNotEmtpty(value, 'Descrição do Produto'),
                      decoration: const InputDecoration(
                        enabled: true,
                        labelText: 'Produto',
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ));
  }

  String? _validateNotEmtpty(String? value, String field) {
    if (value == null 
        || value == '') {
      return '$field inválido!';
    }
    return null;
  }

  void _save() async {
    if (_formKeyProduct.currentState!.validate()) {
      try {
        LoadingService.show(context);
        _product.description = _descriptionController.text;
        _product = await _productsService.save(_product);
      } finally {
        LoadingService.hide(context);
        _redirectList();
      }
    }
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

  void _getArguments() {
    if (_product.id == null) {
      final args = ModalRoute.of(context)!.settings.arguments;
      if (args != null) {
        setState(() {
          _product = args as Product;
          _descriptionController.text = _product.description!;
        });
      }
    }
  }
}
