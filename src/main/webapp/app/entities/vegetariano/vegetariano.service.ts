import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVegetariano } from 'app/shared/model/vegetariano.model';

type EntityResponseType = HttpResponse<IVegetariano>;
type EntityArrayResponseType = HttpResponse<IVegetariano[]>;

@Injectable({ providedIn: 'root' })
export class VegetarianoService {
    public resourceUrl = SERVER_API_URL + 'api/vegetarianos';

    constructor(protected http: HttpClient) {}

    create(vegetariano: IVegetariano): Observable<EntityResponseType> {
        return this.http.post<IVegetariano>(this.resourceUrl, vegetariano, { observe: 'response' });
    }

    update(vegetariano: IVegetariano): Observable<EntityResponseType> {
        return this.http.put<IVegetariano>(this.resourceUrl, vegetariano, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVegetariano>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVegetariano[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
