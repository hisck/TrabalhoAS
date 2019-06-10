/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UniticketTestModule } from '../../../test.module';
import { SaladaUpdateComponent } from 'app/entities/salada/salada-update.component';
import { SaladaService } from 'app/entities/salada/salada.service';
import { Salada } from 'app/shared/model/salada.model';

describe('Component Tests', () => {
    describe('Salada Management Update Component', () => {
        let comp: SaladaUpdateComponent;
        let fixture: ComponentFixture<SaladaUpdateComponent>;
        let service: SaladaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [SaladaUpdateComponent]
            })
                .overrideTemplate(SaladaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SaladaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaladaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Salada(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.salada = entity;
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
                    const entity = new Salada();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.salada = entity;
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
