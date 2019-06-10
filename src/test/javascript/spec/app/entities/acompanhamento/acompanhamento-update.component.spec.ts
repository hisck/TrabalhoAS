/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UniticketTestModule } from '../../../test.module';
import { AcompanhamentoUpdateComponent } from 'app/entities/acompanhamento/acompanhamento-update.component';
import { AcompanhamentoService } from 'app/entities/acompanhamento/acompanhamento.service';
import { Acompanhamento } from 'app/shared/model/acompanhamento.model';

describe('Component Tests', () => {
    describe('Acompanhamento Management Update Component', () => {
        let comp: AcompanhamentoUpdateComponent;
        let fixture: ComponentFixture<AcompanhamentoUpdateComponent>;
        let service: AcompanhamentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [AcompanhamentoUpdateComponent]
            })
                .overrideTemplate(AcompanhamentoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AcompanhamentoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AcompanhamentoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Acompanhamento(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.acompanhamento = entity;
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
                    const entity = new Acompanhamento();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.acompanhamento = entity;
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
