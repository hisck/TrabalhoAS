import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UniticketSharedModule } from 'app/shared';
import {
    AcompanhamentoComponent,
    AcompanhamentoDetailComponent,
    AcompanhamentoUpdateComponent,
    AcompanhamentoDeletePopupComponent,
    AcompanhamentoDeleteDialogComponent,
    acompanhamentoRoute,
    acompanhamentoPopupRoute
} from './';

const ENTITY_STATES = [...acompanhamentoRoute, ...acompanhamentoPopupRoute];

@NgModule({
    imports: [UniticketSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AcompanhamentoComponent,
        AcompanhamentoDetailComponent,
        AcompanhamentoUpdateComponent,
        AcompanhamentoDeleteDialogComponent,
        AcompanhamentoDeletePopupComponent
    ],
    entryComponents: [
        AcompanhamentoComponent,
        AcompanhamentoUpdateComponent,
        AcompanhamentoDeleteDialogComponent,
        AcompanhamentoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UniticketAcompanhamentoModule {}
