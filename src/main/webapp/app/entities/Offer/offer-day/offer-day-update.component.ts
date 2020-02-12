import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IOfferDay } from 'app/shared/model/Offer/offer-day.model';
import { OfferDayService } from './offer-day.service';
import { IOffer } from 'app/shared/model/Offer/offer.model';
import { OfferService } from 'app/entities/Offer/offer';

@Component({
  selector: 'jhi-offer-day-update',
  templateUrl: './offer-day-update.component.html'
})
export class OfferDayUpdateComponent implements OnInit {
  offerDay: IOfferDay;
  isSaving: boolean;

  offers: IOffer[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private offerDayService: OfferDayService,
    private offerService: OfferService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ offerDay }) => {
      this.offerDay = offerDay;
    });
    this.offerService.query().subscribe(
      (res: HttpResponse<IOffer[]>) => {
        this.offers = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.offerDay.id !== undefined) {
      this.subscribeToSaveResponse(this.offerDayService.update(this.offerDay));
    } else {
      this.subscribeToSaveResponse(this.offerDayService.create(this.offerDay));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IOfferDay>>) {
    result.subscribe((res: HttpResponse<IOfferDay>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackOfferById(index: number, item: IOffer) {
    return item.id;
  }
}
