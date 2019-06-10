import { ICardapio } from 'app/shared/model/cardapio.model';

export interface IPratoPrincipal {
    id?: number;
    nomeDoPrato?: string;
    cardapios?: ICardapio[];
}

export class PratoPrincipal implements IPratoPrincipal {
    constructor(public id?: number, public nomeDoPrato?: string, public cardapios?: ICardapio[]) {}
}
