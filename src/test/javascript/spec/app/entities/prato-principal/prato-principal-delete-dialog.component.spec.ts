/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UniticketTestModule } from '../../../test.module';
import { PratoPrincipalDeleteDialogComponent } from 'app/entities/prato-principal/prato-principal-delete-dialog.component';
import { PratoPrincipalService } from 'app/entities/prato-principal/prato-principal.service';

describe('Component Tests', () => {
    describe('PratoPrincipal Management Delete Component', () => {
        let comp: PratoPrincipalDeleteDialogComponent;
        let fixture: ComponentFixture<PratoPrincipalDeleteDialogComponent>;
        let service: PratoPrincipalService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [PratoPrincipalDeleteDialogComponent]
            })
                .overrideTemplate(PratoPrincipalDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PratoPrincipalDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PratoPrincipalService);
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
