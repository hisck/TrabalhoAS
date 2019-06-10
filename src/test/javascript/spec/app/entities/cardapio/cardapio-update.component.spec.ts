/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UniticketTestModule } from '../../../test.module';
import { CardapioUpdateComponent } from 'app/entities/cardapio/cardapio-update.component';
import { CardapioService } from 'app/entities/cardapio/cardapio.service';
import { Cardapio } from 'app/shared/model/cardapio.model';

describe('Component Tests', () => {
    describe('Cardapio Management Update Component', () => {
        let comp: CardapioUpdateComponent;
        let fixture: ComponentFixture<CardapioUpdateComponent>;
        let service: CardapioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [CardapioUpdateComponent]
            })
                .overrideTemplate(CardapioUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CardapioUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CardapioService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Cardapio(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cardapio = entity;
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
                    const entity = new Cardapio();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cardapio = entity;
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
