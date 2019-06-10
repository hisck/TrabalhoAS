import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICardapio } from 'app/shared/model/cardapio.model';

@Component({
    selector: 'jhi-cardapio-detail',
    templateUrl: './cardapio-detail.component.html'
})
export class CardapioDetailComponent implements OnInit {
    cardapio: ICardapio;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cardapio }) => {
            this.cardapio = cardapio;
        });
    }

    previousState() {
        window.history.back();
    }
}
