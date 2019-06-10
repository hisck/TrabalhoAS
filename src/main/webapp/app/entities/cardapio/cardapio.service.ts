import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICardapio } from 'app/shared/model/cardapio.model';

type EntityResponseType = HttpResponse<ICardapio>;
type EntityArrayResponseType = HttpResponse<ICardapio[]>;

@Injectable({ providedIn: 'root' })
export class CardapioService {
    public resourceUrl = SERVER_API_URL + 'api/cardapios';

    constructor(protected http: HttpClient) {}

    create(cardapio: ICardapio): Observable<EntityResponseType> {
        return this.http.post<ICardapio>(this.resourceUrl, cardapio, { observe: 'response' });
    }

    update(cardapio: ICardapio): Observable<EntityResponseType> {
        return this.http.put<ICardapio>(this.resourceUrl, cardapio, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICardapio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICardapio[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
