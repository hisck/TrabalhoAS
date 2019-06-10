import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Sobremesa } from 'app/shared/model/sobremesa.model';
import { SobremesaService } from './sobremesa.service';
import { SobremesaComponent } from './sobremesa.component';
import { SobremesaDetailComponent } from './sobremesa-detail.component';
import { SobremesaUpdateComponent } from './sobremesa-update.component';
import { SobremesaDeletePopupComponent } from './sobremesa-delete-dialog.component';
import { ISobremesa } from 'app/shared/model/sobremesa.model';

@Injectable({ providedIn: 'root' })
export class SobremesaResolve implements Resolve<ISobremesa> {
    constructor(private service: SobremesaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISobremesa> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Sobremesa>) => response.ok),
                map((sobremesa: HttpResponse<Sobremesa>) => sobremesa.body)
            );
        }
        return of(new Sobremesa());
    }
}

export const sobremesaRoute: Routes = [
    {
        path: '',
        component: SobremesaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Sobremesas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SobremesaDetailComponent,
        resolve: {
            sobremesa: SobremesaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sobremesas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SobremesaUpdateComponent,
        resolve: {
            sobremesa: SobremesaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sobremesas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SobremesaUpdateComponent,
        resolve: {
            sobremesa: SobremesaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sobremesas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sobremesaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SobremesaDeletePopupComponent,
        resolve: {
            sobremesa: SobremesaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sobremesas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
