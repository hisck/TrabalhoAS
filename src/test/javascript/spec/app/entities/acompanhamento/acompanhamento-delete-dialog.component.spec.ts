/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UniticketTestModule } from '../../../test.module';
import { AcompanhamentoDeleteDialogComponent } from 'app/entities/acompanhamento/acompanhamento-delete-dialog.component';
import { AcompanhamentoService } from 'app/entities/acompanhamento/acompanhamento.service';

describe('Component Tests', () => {
    describe('Acompanhamento Management Delete Component', () => {
        let comp: AcompanhamentoDeleteDialogComponent;
        let fixture: ComponentFixture<AcompanhamentoDeleteDialogComponent>;
        let service: AcompanhamentoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [AcompanhamentoDeleteDialogComponent]
            })
                .overrideTemplate(AcompanhamentoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AcompanhamentoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AcompanhamentoService);
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
