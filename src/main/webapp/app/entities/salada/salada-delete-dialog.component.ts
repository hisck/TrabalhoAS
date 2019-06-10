import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISalada } from 'app/shared/model/salada.model';
import { SaladaService } from './salada.service';

@Component({
    selector: 'jhi-salada-delete-dialog',
    templateUrl: './salada-delete-dialog.component.html'
})
export class SaladaDeleteDialogComponent {
    salada: ISalada;

    constructor(protected saladaService: SaladaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.saladaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'saladaListModification',
                content: 'Deleted an salada'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-salada-delete-popup',
    template: ''
})
export class SaladaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ salada }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SaladaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.salada = salada;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/salada', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/salada', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
