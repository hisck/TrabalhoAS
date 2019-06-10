import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISobremesa } from 'app/shared/model/sobremesa.model';

@Component({
    selector: 'jhi-sobremesa-detail',
    templateUrl: './sobremesa-detail.component.html'
})
export class SobremesaDetailComponent implements OnInit {
    sobremesa: ISobremesa;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sobremesa }) => {
            this.sobremesa = sobremesa;
        });
    }

    previousState() {
        window.history.back();
    }
}
