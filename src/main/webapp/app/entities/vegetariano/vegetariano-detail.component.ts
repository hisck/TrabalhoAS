import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVegetariano } from 'app/shared/model/vegetariano.model';

@Component({
    selector: 'jhi-vegetariano-detail',
    templateUrl: './vegetariano-detail.component.html'
})
export class VegetarianoDetailComponent implements OnInit {
    vegetariano: IVegetariano;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vegetariano }) => {
            this.vegetariano = vegetariano;
        });
    }

    previousState() {
        window.history.back();
    }
}
