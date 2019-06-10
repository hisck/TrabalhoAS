import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalada } from 'app/shared/model/salada.model';

@Component({
    selector: 'jhi-salada-detail',
    templateUrl: './salada-detail.component.html'
})
export class SaladaDetailComponent implements OnInit {
    salada: ISalada;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ salada }) => {
            this.salada = salada;
        });
    }

    previousState() {
        window.history.back();
    }
}
