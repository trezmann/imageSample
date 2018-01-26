import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { KundeMySuffix } from './kunde-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class KundeMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/kundes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(kunde: KundeMySuffix): Observable<KundeMySuffix> {
        const copy = this.convert(kunde);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(kunde: KundeMySuffix): Observable<KundeMySuffix> {
        const copy = this.convert(kunde);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<KundeMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to KundeMySuffix.
     */
    private convertItemFromServer(json: any): KundeMySuffix {
        const entity: KundeMySuffix = Object.assign(new KundeMySuffix(), json);
        entity.sDatum = this.dateUtils
            .convertLocalDateFromServer(json.sDatum);
        entity.eDatum = this.dateUtils
            .convertLocalDateFromServer(json.eDatum);
        return entity;
    }

    /**
     * Convert a KundeMySuffix to a JSON which can be sent to the server.
     */
    private convert(kunde: KundeMySuffix): KundeMySuffix {
        const copy: KundeMySuffix = Object.assign({}, kunde);
        copy.sDatum = this.dateUtils
            .convertLocalDateToServer(kunde.sDatum);
        copy.eDatum = this.dateUtils
            .convertLocalDateToServer(kunde.eDatum);
        return copy;
    }
}
