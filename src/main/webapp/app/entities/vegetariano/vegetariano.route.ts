import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Vegetariano } from 'app/shared/model/vegetariano.model';
import { VegetarianoService } from './vegetariano.service';
import { VegetarianoComponent } from './vegetariano.component';
import { VegetarianoDetailComponent } from './vegetariano-detail.component';
import { VegetarianoUpdateComponent } from './vegetariano-update.component';
import { VegetarianoDeletePopupComponent } from './vegetariano-delete-dialog.component';
import { IVegetariano } from 'app/shared/model/vegetariano.model';

@Injectable({ providedIn: 'root' })
export class VegetarianoResolve implements Resolve<IVegetariano> {
    constructor(private service: VegetarianoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVegetariano> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Vegetariano>) => response.ok),
                map((vegetariano: HttpResponse<Vegetariano>) => vegetariano.body)
            );
        }
        return of(new Vegetariano());
    }
}

export const vegetarianoRoute: Routes = [
    {
        path: '',
        component: VegetarianoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Vegetarianos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: VegetarianoDetailComponent,
        resolve: {
            vegetariano: VegetarianoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vegetarianos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: VegetarianoUpdateComponent,
        resolve: {
            vegetariano: VegetarianoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vegetarianos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: VegetarianoUpdateComponent,
        resolve: {
            vegetariano: VegetarianoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vegetarianos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vegetarianoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: VegetarianoDeletePopupComponent,
        resolve: {
            vegetariano: VegetarianoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vegetarianos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
