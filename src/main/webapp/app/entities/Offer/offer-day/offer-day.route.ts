import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OfferDay } from 'app/shared/model/Offer/offer-day.model';
import { OfferDayService } from './offer-day.service';
import { OfferDayComponent } from './offer-day.component';
import { OfferDayDetailComponent } from './offer-day-detail.component';
import { OfferDayUpdateComponent } from './offer-day-update.component';
import { OfferDayDeletePopupComponent } from './offer-day-delete-dialog.component';
import { IOfferDay } from 'app/shared/model/Offer/offer-day.model';

@Injectable({ providedIn: 'root' })
export class OfferDayResolve implements Resolve<IOfferDay> {
  constructor(private service: OfferDayService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<OfferDay> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OfferDay>) => response.ok),
        map((offerDay: HttpResponse<OfferDay>) => offerDay.body)
      );
    }
    return of(new OfferDay());
  }
}

export const offerDayRoute: Routes = [
  {
    path: 'offer-day',
    component: OfferDayComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'OfferDays'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'offer-day/:id/view',
    component: OfferDayDetailComponent,
    resolve: {
      offerDay: OfferDayResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OfferDays'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'offer-day/new',
    component: OfferDayUpdateComponent,
    resolve: {
      offerDay: OfferDayResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OfferDays'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'offer-day/:id/edit',
    component: OfferDayUpdateComponent,
    resolve: {
      offerDay: OfferDayResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OfferDays'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const offerDayPopupRoute: Routes = [
  {
    path: 'offer-day/:id/delete',
    component: OfferDayDeletePopupComponent,
    resolve: {
      offerDay: OfferDayResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OfferDays'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
