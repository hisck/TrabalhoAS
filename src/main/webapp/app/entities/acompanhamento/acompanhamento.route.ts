import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Acompanhamento } from 'app/shared/model/acompanhamento.model';
import { AcompanhamentoService } from './acompanhamento.service';
import { AcompanhamentoComponent } from './acompanhamento.component';
import { AcompanhamentoDetailComponent } from './acompanhamento-detail.component';
import { AcompanhamentoUpdateComponent } from './acompanhamento-update.component';
import { AcompanhamentoDeletePopupComponent } from './acompanhamento-delete-dialog.component';
import { IAcompanhamento } from 'app/shared/model/acompanhamento.model';

@Injectable({ providedIn: 'root' })
export class AcompanhamentoResolve implements Resolve<IAcompanhamento> {
    constructor(private service: AcompanhamentoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAcompanhamento> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Acompanhamento>) => response.ok),
                map((acompanhamento: HttpResponse<Acompanhamento>) => acompanhamento.body)
            );
        }
        return of(new Acompanhamento());
    }
}

export const acompanhamentoRoute: Routes = [
    {
        path: '',
        component: AcompanhamentoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Acompanhamentos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AcompanhamentoDetailComponent,
        resolve: {
            acompanhamento: AcompanhamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Acompanhamentos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AcompanhamentoUpdateComponent,
        resolve: {
            acompanhamento: AcompanhamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Acompanhamentos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AcompanhamentoUpdateComponent,
        resolve: {
            acompanhamento: AcompanhamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Acompanhamentos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const acompanhamentoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AcompanhamentoDeletePopupComponent,
        resolve: {
            acompanhamento: AcompanhamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Acompanhamentos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
