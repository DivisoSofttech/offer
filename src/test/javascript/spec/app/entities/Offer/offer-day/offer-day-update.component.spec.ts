/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { OfferDayUpdateComponent } from 'app/entities/Offer/offer-day/offer-day-update.component';
import { OfferDayService } from 'app/entities/Offer/offer-day/offer-day.service';
import { OfferDay } from 'app/shared/model/Offer/offer-day.model';

describe('Component Tests', () => {
  describe('OfferDay Management Update Component', () => {
    let comp: OfferDayUpdateComponent;
    let fixture: ComponentFixture<OfferDayUpdateComponent>;
    let service: OfferDayService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [OfferDayUpdateComponent]
      })
        .overrideTemplate(OfferDayUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OfferDayUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OfferDayService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new OfferDay(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.offerDay = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );

      it(
        'Should call create service on save for new entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new OfferDay();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.offerDay = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.create).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );
    });
  });
});
