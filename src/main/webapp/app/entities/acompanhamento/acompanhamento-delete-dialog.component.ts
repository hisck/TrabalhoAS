import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAcompanhamento } from 'app/shared/model/acompanhamento.model';
import { AcompanhamentoService } from './acompanhamento.service';

@Component({
    selector: 'jhi-acompanhamento-delete-dialog',
    templateUrl: './acompanhamento-delete-dialog.component.html'
})
export class AcompanhamentoDeleteDialogComponent {
    acompanhamento: IAcompanhamento;

    constructor(
        protected acompanhamentoService: AcompanhamentoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.acompanhamentoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'acompanhamentoListModification',
                content: 'Deleted an acompanhamento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-acompanhamento-delete-popup',
    template: ''
})
export class AcompanhamentoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ acompanhamento }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AcompanhamentoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.acompanhamento = acompanhamento;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/acompanhamento', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/acompanhamento', { outlets: { popup: null } }]);
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
