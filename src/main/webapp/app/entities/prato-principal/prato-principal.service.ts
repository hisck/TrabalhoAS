import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPratoPrincipal } from 'app/shared/model/prato-principal.model';

type EntityResponseType = HttpResponse<IPratoPrincipal>;
type EntityArrayResponseType = HttpResponse<IPratoPrincipal[]>;

@Injectable({ providedIn: 'root' })
export class PratoPrincipalService {
    public resourceUrl = SERVER_API_URL + 'api/prato-principals';

    constructor(protected http: HttpClient) {}

    create(pratoPrincipal: IPratoPrincipal): Observable<EntityResponseType> {
        return this.http.post<IPratoPrincipal>(this.resourceUrl, pratoPrincipal, { observe: 'response' });
    }

    update(pratoPrincipal: IPratoPrincipal): Observable<EntityResponseType> {
        return this.http.put<IPratoPrincipal>(this.resourceUrl, pratoPrincipal, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPratoPrincipal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPratoPrincipal[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
