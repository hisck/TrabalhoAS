import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVegetariano } from 'app/shared/model/vegetariano.model';
import { VegetarianoService } from './vegetariano.service';

@Component({
    selector: 'jhi-vegetariano-delete-dialog',
    templateUrl: './vegetariano-delete-dialog.component.html'
})
export class VegetarianoDeleteDialogComponent {
    vegetariano: IVegetariano;

    constructor(
        protected vegetarianoService: VegetarianoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vegetarianoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'vegetarianoListModification',
                content: 'Deleted an vegetariano'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vegetariano-delete-popup',
    template: ''
})
export class VegetarianoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vegetariano }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VegetarianoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.vegetariano = vegetariano;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/vegetariano', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/vegetariano', { outlets: { popup: null } }]);
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
