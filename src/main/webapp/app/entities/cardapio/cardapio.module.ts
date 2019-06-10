import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UniticketSharedModule } from 'app/shared';
import {
    CardapioComponent,
    CardapioDetailComponent,
    CardapioUpdateComponent,
    CardapioDeletePopupComponent,
    CardapioDeleteDialogComponent,
    cardapioRoute,
    cardapioPopupRoute
} from './';

const ENTITY_STATES = [...cardapioRoute, ...cardapioPopupRoute];

@NgModule({
    imports: [UniticketSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CardapioComponent,
        CardapioDetailComponent,
        CardapioUpdateComponent,
        CardapioDeleteDialogComponent,
        CardapioDeletePopupComponent
    ],
    entryComponents: [CardapioComponent, CardapioUpdateComponent, CardapioDeleteDialogComponent, CardapioDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UniticketCardapioModule {}
