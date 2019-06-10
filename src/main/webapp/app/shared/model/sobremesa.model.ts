import { ICardapio } from 'app/shared/model/cardapio.model';

export interface ISobremesa {
    id?: number;
    nomeDaSobremesa?: string;
    cardapios?: ICardapio[];
}

export class Sobremesa implements ISobremesa {
    constructor(public id?: number, public nomeDaSobremesa?: string, public cardapios?: ICardapio[]) {}
}
