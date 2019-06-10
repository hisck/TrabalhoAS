import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICardapio } from 'app/shared/model/cardapio.model';
import { CardapioService } from './cardapio.service';

@Component({
    selector: 'jhi-cardapio-delete-dialog',
    templateUrl: './cardapio-delete-dialog.component.html'
})
export class CardapioDeleteDialogComponent {
    cardapio: ICardapio;

    constructor(protected cardapioService: CardapioService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cardapioService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cardapioListModification',
                content: 'Deleted an cardapio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cardapio-delete-popup',
    template: ''
})
export class CardapioDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cardapio }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CardapioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.cardapio = cardapio;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/cardapio', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/cardapio', { outlets: { popup: null } }]);
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
