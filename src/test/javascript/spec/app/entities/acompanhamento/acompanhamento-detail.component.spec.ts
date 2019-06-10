/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UniticketTestModule } from '../../../test.module';
import { AcompanhamentoDetailComponent } from 'app/entities/acompanhamento/acompanhamento-detail.component';
import { Acompanhamento } from 'app/shared/model/acompanhamento.model';

describe('Component Tests', () => {
    describe('Acompanhamento Management Detail Component', () => {
        let comp: AcompanhamentoDetailComponent;
        let fixture: ComponentFixture<AcompanhamentoDetailComponent>;
        const route = ({ data: of({ acompanhamento: new Acompanhamento(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [AcompanhamentoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AcompanhamentoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AcompanhamentoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.acompanhamento).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
