import 'package:das_angular_mobile/common/services/loading.service.dart';
import 'package:das_angular_mobile/common/widgets/list-item-card.widget.dart';
import 'package:das_angular_mobile/common/widgets/page-title.widget.dart';
import 'package:das_angular_mobile/menu/menu.component.dart';
import 'package:das_angular_mobile/purchase-order/purchase-order.model.dart';
import 'package:das_angular_mobile/purchase-order/services/purchase-orders.service.dart';
import 'package:das_angular_mobile/routes/app_routes.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class PurchaseOrdersPage extends StatefulWidget {

  const PurchaseOrdersPage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _PurchaseOrdersPageState();
}

class _PurchaseOrdersPageState extends State<PurchaseOrdersPage> {
  
  final _formKey = GlobalKey<FormState>();
  final _cpfController = TextEditingController();

  final PurchaseOrderService _purchaseOrderService = PurchaseOrderService();

  List<PurchaseOrder> _list = [];

  @override
  void initState() {
    super.initState();
    _findAll();
  }

  @override
  void dispose() {
    _cpfController.dispose();
    super.dispose();
  }

  _findAll() async {
    LoadingService.show(context);
    List<PurchaseOrder> result = await _purchaseOrderService.findAll();
    setState(() {
      _list = result;
      LoadingService.hide(context);
    });
  }

  _filter() async {
    String cpf = _cpfController.text;
    if (cpf.trim() != '') {
      LoadingService.show(context);
      List<PurchaseOrder> result = await _purchaseOrderService.findByClientCpf(cpf);
      setState(() {
        _list = result;
      });
      LoadingService.hide(context);
    } else {
      _findAll();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Pedido'),
      ),
      drawer: const Menu(),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          await Navigator.pushNamed(context, AppRoutes.PURCHASE_ORDER_REGISTER);
          _filter();
        },
        child: const Icon(Icons.add),
      ),
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
                      controller: _cpfController,
                    ),
                  ),
                  ElevatedButton.icon(
                    onPressed: () { _filter(); }, 
                    icon: const Icon(Icons.search),
                    label: const Text('Filtrar'),
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: ListView.builder(
              padding: const EdgeInsetsDirectional.fromSTEB(0, 10, 0, 120),
              itemCount: _list.length,
              itemBuilder: (context, index) {
                final PurchaseOrder item = _list[index];
                return ListItemCard(
                  {
                    'Id': item.id.toString(),
                    'Data': DateFormat('dd/MM/yyyy').format(item.date!),
                    'Cliente': '${item.client!.firstName!} ${item.client!.lastName!}',
                    'CPF Cliente': item.client!.cpf!,
                  },
                  onEdit: () => _edit(item),
                  onDelete: () => _confirmDelete(item),
                );
              },
            ),
          ),
        ],
      ),
    );
  }

  _edit(PurchaseOrder item) async {
    await Navigator.pushNamed(
      context, 
      AppRoutes.PURCHASE_ORDER_REGISTER, 
      arguments: item
    );
    _filter();
  }

  _confirmDelete(PurchaseOrder item) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text("Pedido"),
          content: const Text("Deseja realmente excluir o pedido?"),
          actions: [
            TextButton(
              child: const Text("Sim"),
              onPressed: () {
                Navigator.pop(context);
                _delete(item);
              }
            ),
            TextButton(
              child: const Text("Não"),
              onPressed: () {
                Navigator.pop(context);
              }
            )
          ]
        );
      }
    );
  }

  _delete(PurchaseOrder item) async {
    LoadingService.show(context);
    await _purchaseOrderService.delete(item.id!);
    LoadingService.hide(context);
    _filter();
  }
}