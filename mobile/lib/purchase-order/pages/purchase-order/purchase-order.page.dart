import 'package:das_angular_mobile/client/client.model.dart';
import 'package:das_angular_mobile/common/widgets/list-item-card.widget.dart';
import 'package:das_angular_mobile/common/widgets/page-title.widget.dart';
import 'package:das_angular_mobile/menu/menu.component.dart';
import 'package:das_angular_mobile/order-item/services/order-item.service.dart';
import 'package:das_angular_mobile/product/product.model.dart';
import 'package:das_angular_mobile/order-item/order-item.model.dart';
import 'package:das_angular_mobile/purchase-order/purchase-order.model.dart';
import 'package:das_angular_mobile/purchase-order/services/purchase-orders.service.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:intl/intl.dart';

class PurchaseOrderPage extends StatefulWidget {

  const PurchaseOrderPage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _PurchaseOrderPageState();
}

class _PurchaseOrderPageState extends State<PurchaseOrderPage> {
  
  final _formKeyOrder = GlobalKey<FormState>();
  final _formKeyItem = GlobalKey<FormState>();
  final _dateController = TextEditingController();
  final _quantityController = TextEditingController();

  final PurchaseOrderService _purchaseOrderService = PurchaseOrderService();
  final OrderItemService _orderItemService = OrderItemService();

  PurchaseOrder _order = PurchaseOrder();
  List<OrderItem> _items = [];
  OrderItem _item = new OrderItem();
  List<Client> _clients = [
    Client(id: 1, cpf: '00000000000', firstName: 'teste', lastName: 'teste'),
    Client(id: 2, cpf: '11111111111', firstName: 'teste 2', lastName: 'teste 2')
  ];
  List<Product> _products = [
    Product(id: 1, description: 'product 1'),
    Product(id: 2, description: 'product 2')
  ];

  @override
  void initState() {
    super.initState();
    _dateController.text = DateFormat('dd/MM/yyyy hh:mm').format(DateTime.now());
  }

  @override
  void dispose() {
    _dateController.dispose();
    _quantityController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Purchase Order'),
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
          const PageTitle('Cadastrar Pedido'),
          Form(
            key: _formKeyOrder,
            child: Container(
              margin: const EdgeInsetsDirectional.fromSTEB(10, 20, 10, 0),
              child: Expanded(
                child: Column(
                  children: [
                    TextFormField(
                      decoration: const InputDecoration(
                        enabled: false,
                      ),
                      controller: _dateController,
                      style: const TextStyle(
                        color: Colors.grey
                      ),
                    ),
                    DropdownButtonFormField(
                      isExpanded: true,
                      hint: const Text("Cliente"),
                      value: _order.client,
                      validator: (Client? value) => _validateClient(value),
                      onChanged: (Client? newValue) {
                        setState(() {
                          _order.client = newValue;
                        });
                      },
                      items: _clients
                        .map((e) => DropdownMenuItem(
                          value: e,
                          child: Text('${e.cpf} - ${e.firstName} ${e.lastName}')
                        ))
                        .toList(),
                    ),
                    Form(
                      key: _formKeyItem,
                      child: Column(
                        children: [
                          DropdownButtonFormField(
                            isExpanded: true,
                            hint: const Text('Produto'),
                            validator: (Product? value) => _validateProduct(value),
                            value: _item.product,
                            onChanged: (Product? newValue) {
                              setState(() {
                                _item.product = newValue;
                              });
                            },
                            items: _products
                              .map((e) => DropdownMenuItem(
                                value: e,
                                child: Text(e.description!)
                              ))
                              .toList(),
                          ),
                          Row (
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              SizedBox(
                                width: 120,
                                child: TextFormField(
                                  keyboardType: TextInputType.number,
                                  inputFormatters: [FilteringTextInputFormatter.digitsOnly],
                                  decoration: const InputDecoration(
                                    hintText: 'Quantidade'
                                  ),
                                  validator: (value) => _validateQuantity(value),
                                  controller: _quantityController,
                                ),
                              ),
                              ElevatedButton.icon(
                                onPressed: () {
                                  _addProduct();
                                }, 
                                label: const Text('Adicionar Produto'),
                                icon: const Icon(Icons.add),
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),
          Expanded(
            child: ListView.builder(
              padding: const EdgeInsetsDirectional.fromSTEB(0, 10, 0, 10),
              itemCount: _items.length,
              itemBuilder: (context, index) {
                final OrderItem item = _items[index];
                return ListItemCard(
                  {
                    'Product': item.product!.description!,
                    'Quantidade': item.quantity.toString(),
                  },
                  onDelete: () {
                    _deleteItem(item);
                  },
                );
              },
            ),
          ),
        ],
      ),
    );
  }

  String? _validateProduct(Product? value) {
    if (value == null) {
      return 'Por favor selecione um Produto!';
    }
    return null;
  }

  String? _validateQuantity(String? value) {
    if (value == null 
        || value == ''
        || int.parse(value) < 0) {
      return 'Quantidade inválida!';
    }
    return null;
  }

  void _addProduct() {
    if (_formKeyItem.currentState!.validate()) {
      _item.quantity = int.parse(_quantityController.text);
      _items.add(_item);
      _clearProductForm();
    }
  }

  void _clearProductForm() {
    _quantityController.text = '';
    setState(() {
      _item = OrderItem();
    });
  }

  _validateClient(Client? value) {
    if (value == null) {
      return 'Por favor selecione um Cliente!';
    }
    return null;
  }

  void _save() async  {
    if (_formKeyOrder.currentState!.validate()
        && _validateItems()) {
      _order.date = DateTime.now();
      showDialog(
        context: context,
        barrierDismissible: false,
        builder: (BuildContext context) {
          return Dialog(
            child: Row(
              mainAxisSize: MainAxisSize.min,
              children: const [
                CircularProgressIndicator(),
              ],
            ),
          );
        },
      );
      try {
        _order = await _purchaseOrderService.save(_order);
        if (_order.id != null) {
          for (var item in _items) {
            item.order = _order;
            await _orderItemService.save(item);
          }
        }
      } finally {
        Navigator.pop(context);
      }
      _redirectList();
    }
  }

  bool _validateItems() {
    if (_items.isEmpty) {
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: const Text("Pedido"),
            content: const Text("Por favor adicionar pelo menos um produto."),
            actions: [
              TextButton(
                child: const Text("OK"),
                onPressed: () {
                  Navigator.pop(context);
                }
              )
            ]
          );
        }
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
          title: const Text("Pedido"),
          content: const Text("Pedido salvo com sucesso"),
          actions: [
            TextButton(
              child: const Text("OK"),
              onPressed: () {
                Navigator.popUntil(context, ModalRoute.withName('/purchase-order'));
              }
            )
          ]
        );
      }
    );
  }

  void _deleteItem(OrderItem item) {
    setState(() {
      _items.remove(item);
    });
  }
}