/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UniticketTestModule } from '../../../test.module';
import { SobremesaDetailComponent } from 'app/entities/sobremesa/sobremesa-detail.component';
import { Sobremesa } from 'app/shared/model/sobremesa.model';

describe('Component Tests', () => {
    describe('Sobremesa Management Detail Component', () => {
        let comp: SobremesaDetailComponent;
        let fixture: ComponentFixture<SobremesaDetailComponent>;
        const route = ({ data: of({ sobremesa: new Sobremesa(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [SobremesaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SobremesaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SobremesaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sobremesa).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
