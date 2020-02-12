/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OfferTestModule } from '../../../../test.module';
import { OfferDayDeleteDialogComponent } from 'app/entities/Offer/offer-day/offer-day-delete-dialog.component';
import { OfferDayService } from 'app/entities/Offer/offer-day/offer-day.service';

describe('Component Tests', () => {
  describe('OfferDay Management Delete Component', () => {
    let comp: OfferDayDeleteDialogComponent;
    let fixture: ComponentFixture<OfferDayDeleteDialogComponent>;
    let service: OfferDayService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [OfferDayDeleteDialogComponent]
      })
        .overrideTemplate(OfferDayDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OfferDayDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OfferDayService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
