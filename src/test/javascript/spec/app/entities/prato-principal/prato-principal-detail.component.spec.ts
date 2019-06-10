/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UniticketTestModule } from '../../../test.module';
import { PratoPrincipalDetailComponent } from 'app/entities/prato-principal/prato-principal-detail.component';
import { PratoPrincipal } from 'app/shared/model/prato-principal.model';

describe('Component Tests', () => {
    describe('PratoPrincipal Management Detail Component', () => {
        let comp: PratoPrincipalDetailComponent;
        let fixture: ComponentFixture<PratoPrincipalDetailComponent>;
        const route = ({ data: of({ pratoPrincipal: new PratoPrincipal(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [UniticketTestModule],
                declarations: [PratoPrincipalDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PratoPrincipalDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PratoPrincipalDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pratoPrincipal).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
