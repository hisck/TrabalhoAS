import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Cardapio } from 'app/shared/model/cardapio.model';
import { CardapioService } from './cardapio.service';
import { CardapioComponent } from './cardapio.component';
import { CardapioDetailComponent } from './cardapio-detail.component';
import { CardapioUpdateComponent } from './cardapio-update.component';
import { CardapioDeletePopupComponent } from './cardapio-delete-dialog.component';
import { ICardapio } from 'app/shared/model/cardapio.model';

@Injectable({ providedIn: 'root' })
export class CardapioResolve implements Resolve<ICardapio> {
    constructor(private service: CardapioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICardapio> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Cardapio>) => response.ok),
                map((cardapio: HttpResponse<Cardapio>) => cardapio.body)
            );
        }
        return of(new Cardapio());
    }
}

export const cardapioRoute: Routes = [
    {
        path: '',
        component: CardapioComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Cardapios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CardapioDetailComponent,
        resolve: {
            cardapio: CardapioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cardapios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CardapioUpdateComponent,
        resolve: {
            cardapio: CardapioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cardapios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CardapioUpdateComponent,
        resolve: {
            cardapio: CardapioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cardapios'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cardapioPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CardapioDeletePopupComponent,
        resolve: {
            cardapio: CardapioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cardapios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
