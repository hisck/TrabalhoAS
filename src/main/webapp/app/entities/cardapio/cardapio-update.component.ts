import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICardapio } from 'app/shared/model/cardapio.model';
import { CardapioService } from './cardapio.service';
import { IPratoPrincipal } from 'app/shared/model/prato-principal.model';
import { PratoPrincipalService } from 'app/entities/prato-principal';
import { IAcompanhamento } from 'app/shared/model/acompanhamento.model';
import { AcompanhamentoService } from 'app/entities/acompanhamento';
import { ISalada } from 'app/shared/model/salada.model';
import { SaladaService } from 'app/entities/salada';
import { IVegetariano } from 'app/shared/model/vegetariano.model';
import { VegetarianoService } from 'app/entities/vegetariano';
import { ISobremesa } from 'app/shared/model/sobremesa.model';
import { SobremesaService } from 'app/entities/sobremesa';

@Component({
    selector: 'jhi-cardapio-update',
    templateUrl: './cardapio-update.component.html'
})
export class CardapioUpdateComponent implements OnInit {
    cardapio: ICardapio;
    isSaving: boolean;

    pratoprincipals: IPratoPrincipal[];

    acompanhamentos: IAcompanhamento[];

    saladas: ISalada[];

    vegetarianos: IVegetariano[];

    sobremesas: ISobremesa[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected cardapioService: CardapioService,
        protected pratoPrincipalService: PratoPrincipalService,
        protected acompanhamentoService: AcompanhamentoService,
        protected saladaService: SaladaService,
        protected vegetarianoService: VegetarianoService,
        protected sobremesaService: SobremesaService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cardapio }) => {
            this.cardapio = cardapio;
        });
        this.pratoPrincipalService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPratoPrincipal[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPratoPrincipal[]>) => response.body)
            )
            .subscribe((res: IPratoPrincipal[]) => (this.pratoprincipals = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.acompanhamentoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAcompanhamento[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAcompanhamento[]>) => response.body)
            )
            .subscribe((res: IAcompanhamento[]) => (this.acompanhamentos = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.saladaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISalada[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISalada[]>) => response.body)
            )
            .subscribe((res: ISalada[]) => (this.saladas = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.vegetarianoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVegetariano[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVegetariano[]>) => response.body)
            )
            .subscribe((res: IVegetariano[]) => (this.vegetarianos = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.sobremesaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISobremesa[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISobremesa[]>) => response.body)
            )
            .subscribe((res: ISobremesa[]) => (this.sobremesas = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cardapio.id !== undefined) {
            this.subscribeToSaveResponse(this.cardapioService.update(this.cardapio));
        } else {
            this.subscribeToSaveResponse(this.cardapioService.create(this.cardapio));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICardapio>>) {
        result.subscribe((res: HttpResponse<ICardapio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPratoPrincipalById(index: number, item: IPratoPrincipal) {
        return item.id;
    }

    trackAcompanhamentoById(index: number, item: IAcompanhamento) {
        return item.id;
    }

    trackSaladaById(index: number, item: ISalada) {
        return item.id;
    }

    trackVegetarianoById(index: number, item: IVegetariano) {
        return item.id;
    }

    trackSobremesaById(index: number, item: ISobremesa) {
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
