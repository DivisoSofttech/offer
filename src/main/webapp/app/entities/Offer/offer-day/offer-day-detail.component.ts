import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOfferDay } from 'app/shared/model/Offer/offer-day.model';

@Component({
  selector: 'jhi-offer-day-detail',
  templateUrl: './offer-day-detail.component.html'
})
export class OfferDayDetailComponent implements OnInit {
  offerDay: IOfferDay;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ offerDay }) => {
      this.offerDay = offerDay;
    });
  }

  previousState() {
    window.history.back();
  }
}
