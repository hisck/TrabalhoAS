import { ICardapio } from 'app/shared/model/cardapio.model';

export interface ISalada {
    id?: number;
    nomeDaSalada?: string;
    cardapios?: ICardapio[];
}

export class Salada implements ISalada {
    constructor(public id?: number, public nomeDaSalada?: string, public cardapios?: ICardapio[]) {}
}
