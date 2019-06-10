import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PratoPrincipal } from 'app/shared/model/prato-principal.model';
import { PratoPrincipalService } from './prato-principal.service';
import { PratoPrincipalComponent } from './prato-principal.component';
import { PratoPrincipalDetailComponent } from './prato-principal-detail.component';
import { PratoPrincipalUpdateComponent } from './prato-principal-update.component';
import { PratoPrincipalDeletePopupComponent } from './prato-principal-delete-dialog.component';
import { IPratoPrincipal } from 'app/shared/model/prato-principal.model';

@Injectable({ providedIn: 'root' })
export class PratoPrincipalResolve implements Resolve<IPratoPrincipal> {
    constructor(private service: PratoPrincipalService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPratoPrincipal> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PratoPrincipal>) => response.ok),
                map((pratoPrincipal: HttpResponse<PratoPrincipal>) => pratoPrincipal.body)
            );
        }
        return of(new PratoPrincipal());
    }
}

export const pratoPrincipalRoute: Routes = [
    {
        path: '',
        component: PratoPrincipalComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'PratoPrincipals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PratoPrincipalDetailComponent,
        resolve: {
            pratoPrincipal: PratoPrincipalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PratoPrincipals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PratoPrincipalUpdateComponent,
        resolve: {
            pratoPrincipal: PratoPrincipalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PratoPrincipals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PratoPrincipalUpdateComponent,
        resolve: {
            pratoPrincipal: PratoPrincipalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PratoPrincipals'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pratoPrincipalPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PratoPrincipalDeletePopupComponent,
        resolve: {
            pratoPrincipal: PratoPrincipalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PratoPrincipals'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
