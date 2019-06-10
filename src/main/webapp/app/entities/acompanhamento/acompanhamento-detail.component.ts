import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAcompanhamento } from 'app/shared/model/acompanhamento.model';

@Component({
    selector: 'jhi-acompanhamento-detail',
    templateUrl: './acompanhamento-detail.component.html'
})
export class AcompanhamentoDetailComponent implements OnInit {
    acompanhamento: IAcompanhamento;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ acompanhamento }) => {
            this.acompanhamento = acompanhamento;
        });
    }

    previousState() {
        window.history.back();
    }
}
