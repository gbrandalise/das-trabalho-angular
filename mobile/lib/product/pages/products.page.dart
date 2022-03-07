import 'package:das_angular_mobile/common/widgets/page-title.widget.dart';
import 'package:das_angular_mobile/menu/menu.component.dart';
import 'package:das_angular_mobile/product/product.model.dart';
import 'package:das_angular_mobile/product/services/product.service.dart';
import 'package:flutter/material.dart';
import '../../common/services/loading.service.dart';
import '../../common/widgets/list-item-card.widget.dart';
import '../../routes/app_routes.dart';

class ProductsPage extends StatefulWidget {
  const ProductsPage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _ProductsPageState();
}

class _ProductsPageState extends State<ProductsPage> {
  final ProductService productsService = ProductService();

  List<Product> _list = [];

  @override
  void initState() {
    super.initState();
    _findAll();
  }

  _findAll() async {
    List<Product> result = await productsService.findAll();
    setState(() {
      _list = result;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Produto'),
      ),
      drawer: const Menu(),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          await Navigator.pushNamed(context, AppRoutes.PRODUCT_REGISTER);
          _findAll();
        },
        child: const Icon(Icons.add),
      ),
      body: Column(
        children: [
          const PageTitle('Lista de Produtos'),
          Expanded(
            child: ListView.builder(
              padding: const EdgeInsetsDirectional.fromSTEB(0, 10, 0, 100),
              itemCount: _list.length,
              itemBuilder: (context, index) {
                final Product item = _list[index];
                return ListItemCard(
                  {
                    'Id': item.id.toString(),
                    'Produto': item.description.toString(),
                  },
                  onEdit: () {},
                  onDelete: () => _confirmDelete(item),
                );
              },
            ),
          ),
        ],
      ),
    );
  }

  _confirmDelete(Product item) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text("Produto"),
          content: const Text("Deseja realmente excluir o produto?"),
          actions: [
            TextButton(
              child: const Text("Sim"),
              onPressed: () {
                Navigator.pop(context);
                _delete(item);
              },
            ),
            TextButton(
              child: const Text("Não"),
              onPressed: () {
                Navigator.pop(context);
              },
            )
          ],
        );
      },
    );
  }

  _delete(Product product) async {
    LoadingService.show(context);
    await productsService.delete(product.id!);
    LoadingService.hide(context);
    _findAll();
  }
}
