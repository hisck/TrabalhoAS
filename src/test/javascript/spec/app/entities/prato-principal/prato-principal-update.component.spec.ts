/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UniticketTestModule } from '../../../test.module';
import { PratoPrincipalUpdateComponent } from 'app/entities/prato-principal/prato-principal-update.component';
import { PratoPrincipalService } from 'app/entities/prato-principal/prato-principal.service';
import { PratoPrincipal } from 'app/shared/model/prato-principal.model';

describe('Component Tests', () => {
    describe('PratoPrincipal Management Update Component', () => {
        let comp: PratoPrincipalUpdateComponent;
        let fixture: ComponentFixture<PratoPrincipalUpdateComponent>;
        let service: PratoPrincipalService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [PratoPrincipalUpdateComponent]
            })
                .overrideTemplate(PratoPrincipalUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PratoPrincipalUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PratoPrincipalService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PratoPrincipal(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pratoPrincipal = entity;
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
                    const entity = new PratoPrincipal();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pratoPrincipal = entity;
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
