/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UniticketTestModule } from '../../../test.module';
import { CardapioDetailComponent } from 'app/entities/cardapio/cardapio-detail.component';
import { Cardapio } from 'app/shared/model/cardapio.model';

describe('Component Tests', () => {
    describe('Cardapio Management Detail Component', () => {
        let comp: CardapioDetailComponent;
        let fixture: ComponentFixture<CardapioDetailComponent>;
        const route = ({ data: of({ cardapio: new Cardapio(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [CardapioDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CardapioDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CardapioDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cardapio).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
