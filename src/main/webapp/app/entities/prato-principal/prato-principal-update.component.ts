import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPratoPrincipal } from 'app/shared/model/prato-principal.model';
import { PratoPrincipalService } from './prato-principal.service';
import { ICardapio } from 'app/shared/model/cardapio.model';
import { CardapioService } from 'app/entities/cardapio';

@Component({
    selector: 'jhi-prato-principal-update',
    templateUrl: './prato-principal-update.component.html'
})
export class PratoPrincipalUpdateComponent implements OnInit {
    pratoPrincipal: IPratoPrincipal;
    isSaving: boolean;

    cardapios: ICardapio[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected pratoPrincipalService: PratoPrincipalService,
        protected cardapioService: CardapioService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pratoPrincipal }) => {
            this.pratoPrincipal = pratoPrincipal;
        });
        this.cardapioService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICardapio[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICardapio[]>) => response.body)
            )
            .subscribe((res: ICardapio[]) => (this.cardapios = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pratoPrincipal.id !== undefined) {
            this.subscribeToSaveResponse(this.pratoPrincipalService.update(this.pratoPrincipal));
        } else {
            this.subscribeToSaveResponse(this.pratoPrincipalService.create(this.pratoPrincipal));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPratoPrincipal>>) {
        result.subscribe((res: HttpResponse<IPratoPrincipal>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCardapioById(index: number, item: ICardapio) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
