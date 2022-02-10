import 'package:das_angular_mobile/product/product.model.dart';
import 'package:das_angular_mobile/purchase-order/purchase-order.model.dart';

class OrderItem {
  int? id;
  int? quantity;
  PurchaseOrder? order;
  Product? product;

  OrderItem({this.id, this.quantity, this.order, this.product});
}