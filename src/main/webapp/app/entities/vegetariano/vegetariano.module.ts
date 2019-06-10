import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UniticketSharedModule } from 'app/shared';
import {
    VegetarianoComponent,
    VegetarianoDetailComponent,
    VegetarianoUpdateComponent,
    VegetarianoDeletePopupComponent,
    VegetarianoDeleteDialogComponent,
    vegetarianoRoute,
    vegetarianoPopupRoute
} from './';

const ENTITY_STATES = [...vegetarianoRoute, ...vegetarianoPopupRoute];

@NgModule({
    imports: [UniticketSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VegetarianoComponent,
        VegetarianoDetailComponent,
        VegetarianoUpdateComponent,
        VegetarianoDeleteDialogComponent,
        VegetarianoDeletePopupComponent
    ],
    entryComponents: [VegetarianoComponent, VegetarianoUpdateComponent, VegetarianoDeleteDialogComponent, VegetarianoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UniticketVegetarianoModule {}
