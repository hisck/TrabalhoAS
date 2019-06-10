/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UniticketTestModule } from '../../../test.module';
import { SobremesaUpdateComponent } from 'app/entities/sobremesa/sobremesa-update.component';
import { SobremesaService } from 'app/entities/sobremesa/sobremesa.service';
import { Sobremesa } from 'app/shared/model/sobremesa.model';

describe('Component Tests', () => {
    describe('Sobremesa Management Update Component', () => {
        let comp: SobremesaUpdateComponent;
        let fixture: ComponentFixture<SobremesaUpdateComponent>;
        let service: SobremesaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [SobremesaUpdateComponent]
            })
                .overrideTemplate(SobremesaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SobremesaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SobremesaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Sobremesa(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sobremesa = entity;
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
                    const entity = new Sobremesa();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sobremesa = entity;
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
