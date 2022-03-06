import 'package:das_angular_mobile/common/services/loading.service.dart';
import 'package:das_angular_mobile/common/widgets/list-item-card.widget.dart';
import 'package:das_angular_mobile/common/widgets/page-title.widget.dart';
import 'package:das_angular_mobile/menu/menu.component.dart';
import 'package:das_angular_mobile/order-item/order-item.model.dart';
import 'package:das_angular_mobile/order-item/services/order-item.service.dart';
import 'package:flutter/material.dart';

class OrderItemsPage extends StatefulWidget {

  const OrderItemsPage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _OrderItemsPageState();
}

class _OrderItemsPageState extends State<OrderItemsPage> {
  
  final OrderItemService _orderItemService = OrderItemService();

  int? _idPedido;
  List<OrderItem> _list = [];

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    _getArguments();
    return Scaffold(
      appBar: AppBar(
        title: const Text('Pedido'),
      ),
      drawer: const Menu(),
      body: Column(
        children: [
          PageTitle('Items do Pedido $_idPedido'),
          Expanded(
            child: ListView.builder(
              padding: const EdgeInsetsDirectional.fromSTEB(0, 10, 0, 120),
              itemCount: _list.length,
              itemBuilder: (context, index) {
                final OrderItem item = _list[index];
                return ListItemCard(
                  {
                    'Id': item.id.toString(),
                    'Produto': item.product!.description!,
                    'Quantidade': item.quantity.toString(),
                  }
                );
              },
            ),
          ),
        ],
      ),
    );
  }

  void _getArguments() {
    if (_idPedido == null) {
      _idPedido = ModalRoute.of(context)!.settings.arguments as int;
      _findItemsByOrder();
    }
  }

  _findItemsByOrder() async {
    LoadingService.show(context);
    var result = await _orderItemService.findByOrderId(_idPedido!);
    setState(() {
      _list = result;
    });
    LoadingService.hide(context);
  }
}