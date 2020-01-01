import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Offer } from 'app/shared/model/Offer/offer.model';
import { OfferService } from './offer.service';
import { OfferComponent } from './offer.component';
import { OfferDetailComponent } from './offer-detail.component';
import { OfferUpdateComponent } from './offer-update.component';
import { OfferDeletePopupComponent } from './offer-delete-dialog.component';
import { IOffer } from 'app/shared/model/Offer/offer.model';

@Injectable({ providedIn: 'root' })
export class OfferResolve implements Resolve<IOffer> {
  constructor(private service: OfferService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Offer> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Offer>) => response.ok),
        map((offer: HttpResponse<Offer>) => offer.body)
      );
    }
    return of(new Offer());
  }
}

export const offerRoute: Routes = [
  {
    path: 'offer',
    component: OfferComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Offers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'offer/:id/view',
    component: OfferDetailComponent,
    resolve: {
      offer: OfferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Offers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'offer/new',
    component: OfferUpdateComponent,
    resolve: {
      offer: OfferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Offers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'offer/:id/edit',
    component: OfferUpdateComponent,
    resolve: {
      offer: OfferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Offers'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const offerPopupRoute: Routes = [
  {
    path: 'offer/:id/delete',
    component: OfferDeletePopupComponent,
    resolve: {
      offer: OfferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Offers'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
