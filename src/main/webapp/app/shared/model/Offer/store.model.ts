export interface IStore {
  id?: number;
  storeId?: string;
  outletId?: number;
  offerId?: number;
}

export class Store implements IStore {
  constructor(public id?: number, public storeId?: string, public outletId?: number, public offerId?: number) {}
}
