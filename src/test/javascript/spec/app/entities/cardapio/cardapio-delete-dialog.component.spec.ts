/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UniticketTestModule } from '../../../test.module';
import { CardapioDeleteDialogComponent } from 'app/entities/cardapio/cardapio-delete-dialog.component';
import { CardapioService } from 'app/entities/cardapio/cardapio.service';

describe('Component Tests', () => {
    describe('Cardapio Management Delete Component', () => {
        let comp: CardapioDeleteDialogComponent;
        let fixture: ComponentFixture<CardapioDeleteDialogComponent>;
        let service: CardapioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [CardapioDeleteDialogComponent]
            })
                .overrideTemplate(CardapioDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CardapioDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CardapioService);
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
