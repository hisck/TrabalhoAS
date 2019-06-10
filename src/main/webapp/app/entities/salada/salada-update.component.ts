import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISalada } from 'app/shared/model/salada.model';
import { SaladaService } from './salada.service';
import { ICardapio } from 'app/shared/model/cardapio.model';
import { CardapioService } from 'app/entities/cardapio';

@Component({
    selector: 'jhi-salada-update',
    templateUrl: './salada-update.component.html'
})
export class SaladaUpdateComponent implements OnInit {
    salada: ISalada;
    isSaving: boolean;

    cardapios: ICardapio[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected saladaService: SaladaService,
        protected cardapioService: CardapioService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ salada }) => {
            this.salada = salada;
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
        if (this.salada.id !== undefined) {
            this.subscribeToSaveResponse(this.saladaService.update(this.salada));
        } else {
            this.subscribeToSaveResponse(this.saladaService.create(this.salada));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalada>>) {
        result.subscribe((res: HttpResponse<ISalada>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
