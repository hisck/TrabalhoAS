import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'prato-principal',
                loadChildren: './prato-principal/prato-principal.module#UniticketPratoPrincipalModule'
            },
            {
                path: 'acompanhamento',
                loadChildren: './acompanhamento/acompanhamento.module#UniticketAcompanhamentoModule'
            },
            {
                path: 'salada',
                loadChildren: './salada/salada.module#UniticketSaladaModule'
            },
            {
                path: 'vegetariano',
                loadChildren: './vegetariano/vegetariano.module#UniticketVegetarianoModule'
            },
            {
                path: 'sobremesa',
                loadChildren: './sobremesa/sobremesa.module#UniticketSobremesaModule'
            },
            {
                path: 'cardapio',
                loadChildren: './cardapio/cardapio.module#UniticketCardapioModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UniticketEntityModule {}
