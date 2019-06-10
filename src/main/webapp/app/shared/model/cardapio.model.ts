import { IPratoPrincipal } from 'app/shared/model/prato-principal.model';
import { IAcompanhamento } from 'app/shared/model/acompanhamento.model';
import { ISalada } from 'app/shared/model/salada.model';
import { IVegetariano } from 'app/shared/model/vegetariano.model';
import { ISobremesa } from 'app/shared/model/sobremesa.model';

export interface ICardapio {
    id?: number;
    dia?: string;
    principals?: IPratoPrincipal[];
    acompanhamentos?: IAcompanhamento[];
    saladas?: ISalada[];
    vegetarianos?: IVegetariano[];
    sobremesas?: ISobremesa[];
}

export class Cardapio implements ICardapio {
    constructor(
        public id?: number,
        public dia?: string,
        public principals?: IPratoPrincipal[],
        public acompanhamentos?: IAcompanhamento[],
        public saladas?: ISalada[],
        public vegetarianos?: IVegetariano[],
        public sobremesas?: ISobremesa[]
    ) {}
}
