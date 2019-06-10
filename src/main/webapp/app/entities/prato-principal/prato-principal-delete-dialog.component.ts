import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPratoPrincipal } from 'app/shared/model/prato-principal.model';
import { PratoPrincipalService } from './prato-principal.service';

@Component({
    selector: 'jhi-prato-principal-delete-dialog',
    templateUrl: './prato-principal-delete-dialog.component.html'
})
export class PratoPrincipalDeleteDialogComponent {
    pratoPrincipal: IPratoPrincipal;

    constructor(
        protected pratoPrincipalService: PratoPrincipalService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pratoPrincipalService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pratoPrincipalListModification',
                content: 'Deleted an pratoPrincipal'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prato-principal-delete-popup',
    template: ''
})
export class PratoPrincipalDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pratoPrincipal }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PratoPrincipalDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.pratoPrincipal = pratoPrincipal;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/prato-principal', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/prato-principal', { outlets: { popup: null } }]);
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
