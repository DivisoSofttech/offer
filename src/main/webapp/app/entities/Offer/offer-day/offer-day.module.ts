import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OfferSharedModule } from 'app/shared';
import {
  OfferDayComponent,
  OfferDayDetailComponent,
  OfferDayUpdateComponent,
  OfferDayDeletePopupComponent,
  OfferDayDeleteDialogComponent,
  offerDayRoute,
  offerDayPopupRoute
} from './';

const ENTITY_STATES = [...offerDayRoute, ...offerDayPopupRoute];

@NgModule({
  imports: [OfferSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OfferDayComponent,
    OfferDayDetailComponent,
    OfferDayUpdateComponent,
    OfferDayDeleteDialogComponent,
    OfferDayDeletePopupComponent
  ],
  entryComponents: [OfferDayComponent, OfferDayUpdateComponent, OfferDayDeleteDialogComponent, OfferDayDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OfferOfferDayModule {}
