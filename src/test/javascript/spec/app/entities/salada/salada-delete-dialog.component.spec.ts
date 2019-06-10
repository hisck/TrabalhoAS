/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UniticketTestModule } from '../../../test.module';
import { SaladaDeleteDialogComponent } from 'app/entities/salada/salada-delete-dialog.component';
import { SaladaService } from 'app/entities/salada/salada.service';

describe('Component Tests', () => {
    describe('Salada Management Delete Component', () => {
        let comp: SaladaDeleteDialogComponent;
        let fixture: ComponentFixture<SaladaDeleteDialogComponent>;
        let service: SaladaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [SaladaDeleteDialogComponent]
            })
                .overrideTemplate(SaladaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SaladaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaladaService);
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
