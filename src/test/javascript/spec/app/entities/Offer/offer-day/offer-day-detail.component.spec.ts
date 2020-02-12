/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { OfferDayDetailComponent } from 'app/entities/Offer/offer-day/offer-day-detail.component';
import { OfferDay } from 'app/shared/model/Offer/offer-day.model';

describe('Component Tests', () => {
  describe('OfferDay Management Detail Component', () => {
    let comp: OfferDayDetailComponent;
    let fixture: ComponentFixture<OfferDayDetailComponent>;
    const route = ({ data: of({ offerDay: new OfferDay(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [OfferDayDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OfferDayDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OfferDayDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.offerDay).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
