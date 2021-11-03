import { Product } from "./product.model";
import { PurchaseOrder } from "./purchase-order.model";

export class OrderItem {
  constructor(
    public id?: number,
    public quantity?: number,
    public order?: PurchaseOrder,
    public product?: Product,
  ) { }
}
