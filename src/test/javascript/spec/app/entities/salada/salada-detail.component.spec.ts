/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UniticketTestModule } from '../../../test.module';
import { SaladaDetailComponent } from 'app/entities/salada/salada-detail.component';
import { Salada } from 'app/shared/model/salada.model';

describe('Component Tests', () => {
    describe('Salada Management Detail Component', () => {
        let comp: SaladaDetailComponent;
        let fixture: ComponentFixture<SaladaDetailComponent>;
        const route = ({ data: of({ salada: new Salada(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [SaladaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SaladaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SaladaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.salada).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
