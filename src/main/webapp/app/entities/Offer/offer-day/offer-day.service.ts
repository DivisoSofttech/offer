import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOfferDay } from 'app/shared/model/Offer/offer-day.model';

type EntityResponseType = HttpResponse<IOfferDay>;
type EntityArrayResponseType = HttpResponse<IOfferDay[]>;

@Injectable({ providedIn: 'root' })
export class OfferDayService {
  public resourceUrl = SERVER_API_URL + 'api/offer-days';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/offer-days';

  constructor(private http: HttpClient) {}

  create(offerDay: IOfferDay): Observable<EntityResponseType> {
    return this.http.post<IOfferDay>(this.resourceUrl, offerDay, { observe: 'response' });
  }

  update(offerDay: IOfferDay): Observable<EntityResponseType> {
    return this.http.put<IOfferDay>(this.resourceUrl, offerDay, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOfferDay>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOfferDay[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOfferDay[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
