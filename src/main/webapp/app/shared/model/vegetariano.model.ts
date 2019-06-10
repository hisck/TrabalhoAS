import { ICardapio } from 'app/shared/model/cardapio.model';

export interface IVegetariano {
    id?: number;
    nomeDoPrato?: string;
    cardapios?: ICardapio[];
}

export class Vegetariano implements IVegetariano {
    constructor(public id?: number, public nomeDoPrato?: string, public cardapios?: ICardapio[]) {}
}
