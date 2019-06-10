import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UniticketSharedModule } from 'app/shared';
import {
    SobremesaComponent,
    SobremesaDetailComponent,
    SobremesaUpdateComponent,
    SobremesaDeletePopupComponent,
    SobremesaDeleteDialogComponent,
    sobremesaRoute,
    sobremesaPopupRoute
} from './';

const ENTITY_STATES = [...sobremesaRoute, ...sobremesaPopupRoute];

@NgModule({
    imports: [UniticketSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SobremesaComponent,
        SobremesaDetailComponent,
        SobremesaUpdateComponent,
        SobremesaDeleteDialogComponent,
        SobremesaDeletePopupComponent
    ],
    entryComponents: [SobremesaComponent, SobremesaUpdateComponent, SobremesaDeleteDialogComponent, SobremesaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UniticketSobremesaModule {}
