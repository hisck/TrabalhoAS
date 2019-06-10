import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UniticketSharedModule } from 'app/shared';
import {
    SaladaComponent,
    SaladaDetailComponent,
    SaladaUpdateComponent,
    SaladaDeletePopupComponent,
    SaladaDeleteDialogComponent,
    saladaRoute,
    saladaPopupRoute
} from './';

const ENTITY_STATES = [...saladaRoute, ...saladaPopupRoute];

@NgModule({
    imports: [UniticketSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SaladaComponent, SaladaDetailComponent, SaladaUpdateComponent, SaladaDeleteDialogComponent, SaladaDeletePopupComponent],
    entryComponents: [SaladaComponent, SaladaUpdateComponent, SaladaDeleteDialogComponent, SaladaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UniticketSaladaModule {}
