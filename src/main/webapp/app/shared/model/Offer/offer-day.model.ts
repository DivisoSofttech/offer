export interface IOfferDay {
  id?: number;
  day?: string;
  offerId?: number;
}

export class OfferDay implements IOfferDay {
  constructor(public id?: number, public day?: string, public offerId?: number) {}
}
