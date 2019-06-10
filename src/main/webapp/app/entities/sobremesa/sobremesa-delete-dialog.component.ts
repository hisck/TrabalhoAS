import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISobremesa } from 'app/shared/model/sobremesa.model';
import { SobremesaService } from './sobremesa.service';

@Component({
    selector: 'jhi-sobremesa-delete-dialog',
    templateUrl: './sobremesa-delete-dialog.component.html'
})
export class SobremesaDeleteDialogComponent {
    sobremesa: ISobremesa;

    constructor(
        protected sobremesaService: SobremesaService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sobremesaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sobremesaListModification',
                content: 'Deleted an sobremesa'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sobremesa-delete-popup',
    template: ''
})
export class SobremesaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sobremesa }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SobremesaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.sobremesa = sobremesa;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/sobremesa', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/sobremesa', { outlets: { popup: null } }]);
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
