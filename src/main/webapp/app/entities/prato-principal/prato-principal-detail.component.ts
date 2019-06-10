import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPratoPrincipal } from 'app/shared/model/prato-principal.model';

@Component({
    selector: 'jhi-prato-principal-detail',
    templateUrl: './prato-principal-detail.component.html'
})
export class PratoPrincipalDetailComponent implements OnInit {
    pratoPrincipal: IPratoPrincipal;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pratoPrincipal }) => {
            this.pratoPrincipal = pratoPrincipal;
        });
    }

    previousState() {
        window.history.back();
    }
}
