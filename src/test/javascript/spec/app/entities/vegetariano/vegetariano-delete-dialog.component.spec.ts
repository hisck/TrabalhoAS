/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UniticketTestModule } from '../../../test.module';
import { VegetarianoDeleteDialogComponent } from 'app/entities/vegetariano/vegetariano-delete-dialog.component';
import { VegetarianoService } from 'app/entities/vegetariano/vegetariano.service';

describe('Component Tests', () => {
    describe('Vegetariano Management Delete Component', () => {
        let comp: VegetarianoDeleteDialogComponent;
        let fixture: ComponentFixture<VegetarianoDeleteDialogComponent>;
        let service: VegetarianoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [VegetarianoDeleteDialogComponent]
            })
                .overrideTemplate(VegetarianoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VegetarianoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VegetarianoService);
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
