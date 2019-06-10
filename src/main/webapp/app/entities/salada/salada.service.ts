import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISalada } from 'app/shared/model/salada.model';

type EntityResponseType = HttpResponse<ISalada>;
type EntityArrayResponseType = HttpResponse<ISalada[]>;

@Injectable({ providedIn: 'root' })
export class SaladaService {
    public resourceUrl = SERVER_API_URL + 'api/saladas';

    constructor(protected http: HttpClient) {}

    create(salada: ISalada): Observable<EntityResponseType> {
        return this.http.post<ISalada>(this.resourceUrl, salada, { observe: 'response' });
    }

    update(salada: ISalada): Observable<EntityResponseType> {
        return this.http.put<ISalada>(this.resourceUrl, salada, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISalada>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISalada[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
