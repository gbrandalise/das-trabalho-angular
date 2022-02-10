import 'package:das_angular_mobile/client/client.model.dart';

class PurchaseOrder {
    int? id;
    DateTime? date;
    Client? client;

    PurchaseOrder(this.id, this.date, this.client);
}