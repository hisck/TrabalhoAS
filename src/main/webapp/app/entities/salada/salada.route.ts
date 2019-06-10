import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Salada } from 'app/shared/model/salada.model';
import { SaladaService } from './salada.service';
import { SaladaComponent } from './salada.component';
import { SaladaDetailComponent } from './salada-detail.component';
import { SaladaUpdateComponent } from './salada-update.component';
import { SaladaDeletePopupComponent } from './salada-delete-dialog.component';
import { ISalada } from 'app/shared/model/salada.model';

@Injectable({ providedIn: 'root' })
export class SaladaResolve implements Resolve<ISalada> {
    constructor(private service: SaladaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISalada> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Salada>) => response.ok),
                map((salada: HttpResponse<Salada>) => salada.body)
            );
        }
        return of(new Salada());
    }
}

export const saladaRoute: Routes = [
    {
        path: '',
        component: SaladaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Saladas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SaladaDetailComponent,
        resolve: {
            salada: SaladaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Saladas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SaladaUpdateComponent,
        resolve: {
            salada: SaladaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Saladas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SaladaUpdateComponent,
        resolve: {
            salada: SaladaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Saladas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const saladaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SaladaDeletePopupComponent,
        resolve: {
            salada: SaladaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Saladas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
