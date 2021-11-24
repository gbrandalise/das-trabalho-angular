import { Client } from "./client.model";

export class PurchaseOrder {
  constructor(
    public id?: number,
    public date?: Date,
    public client?: Client,
  ) { }
}
