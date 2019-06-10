import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISobremesa } from 'app/shared/model/sobremesa.model';
import { SobremesaService } from './sobremesa.service';
import { ICardapio } from 'app/shared/model/cardapio.model';
import { CardapioService } from 'app/entities/cardapio';

@Component({
    selector: 'jhi-sobremesa-update',
    templateUrl: './sobremesa-update.component.html'
})
export class SobremesaUpdateComponent implements OnInit {
    sobremesa: ISobremesa;
    isSaving: boolean;

    cardapios: ICardapio[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected sobremesaService: SobremesaService,
        protected cardapioService: CardapioService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sobremesa }) => {
            this.sobremesa = sobremesa;
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
        if (this.sobremesa.id !== undefined) {
            this.subscribeToSaveResponse(this.sobremesaService.update(this.sobremesa));
        } else {
            this.subscribeToSaveResponse(this.sobremesaService.create(this.sobremesa));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISobremesa>>) {
        result.subscribe((res: HttpResponse<ISobremesa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
