import { ICardapio } from 'app/shared/model/cardapio.model';

export interface IAcompanhamento {
    id?: number;
    nomeDoAcompanhamento?: string;
    cardapios?: ICardapio[];
}

export class Acompanhamento implements IAcompanhamento {
    constructor(public id?: number, public nomeDoAcompanhamento?: string, public cardapios?: ICardapio[]) {}
}
