/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UniticketTestModule } from '../../../test.module';
import { VegetarianoDetailComponent } from 'app/entities/vegetariano/vegetariano-detail.component';
import { Vegetariano } from 'app/shared/model/vegetariano.model';

describe('Component Tests', () => {
    describe('Vegetariano Management Detail Component', () => {
        let comp: VegetarianoDetailComponent;
        let fixture: ComponentFixture<VegetarianoDetailComponent>;
        const route = ({ data: of({ vegetariano: new Vegetariano(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [VegetarianoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VegetarianoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VegetarianoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.vegetariano).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
