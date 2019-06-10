import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UniticketSharedModule } from 'app/shared';
import {
    PratoPrincipalComponent,
    PratoPrincipalDetailComponent,
    PratoPrincipalUpdateComponent,
    PratoPrincipalDeletePopupComponent,
    PratoPrincipalDeleteDialogComponent,
    pratoPrincipalRoute,
    pratoPrincipalPopupRoute
} from './';

const ENTITY_STATES = [...pratoPrincipalRoute, ...pratoPrincipalPopupRoute];

@NgModule({
    imports: [UniticketSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PratoPrincipalComponent,
        PratoPrincipalDetailComponent,
        PratoPrincipalUpdateComponent,
        PratoPrincipalDeleteDialogComponent,
        PratoPrincipalDeletePopupComponent
    ],
    entryComponents: [
        PratoPrincipalComponent,
        PratoPrincipalUpdateComponent,
        PratoPrincipalDeleteDialogComponent,
        PratoPrincipalDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UniticketPratoPrincipalModule {}
