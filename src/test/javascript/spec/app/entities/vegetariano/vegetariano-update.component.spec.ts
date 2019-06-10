/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UniticketTestModule } from '../../../test.module';
import { VegetarianoUpdateComponent } from 'app/entities/vegetariano/vegetariano-update.component';
import { VegetarianoService } from 'app/entities/vegetariano/vegetariano.service';
import { Vegetariano } from 'app/shared/model/vegetariano.model';

describe('Component Tests', () => {
    describe('Vegetariano Management Update Component', () => {
        let comp: VegetarianoUpdateComponent;
        let fixture: ComponentFixture<VegetarianoUpdateComponent>;
        let service: VegetarianoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [VegetarianoUpdateComponent]
            })
                .overrideTemplate(VegetarianoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VegetarianoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VegetarianoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Vegetariano(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vegetariano = entity;
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
                    const entity = new Vegetariano();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vegetariano = entity;
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
