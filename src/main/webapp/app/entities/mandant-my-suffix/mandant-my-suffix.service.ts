import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { MandantMySuffix } from './mandant-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MandantMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/mandants';

    constructor(private http: Http) { }

    create(mandant: MandantMySuffix): Observable<MandantMySuffix> {
        const copy = this.convert(mandant);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(mandant: MandantMySuffix): Observable<MandantMySuffix> {
        const copy = this.convert(mandant);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MandantMySuffix> {
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
     * Convert a returned JSON object to MandantMySuffix.
     */
    private convertItemFromServer(json: any): MandantMySuffix {
        const entity: MandantMySuffix = Object.assign(new MandantMySuffix(), json);
        return entity;
    }

    /**
     * Convert a MandantMySuffix to a JSON which can be sent to the server.
     */
    private convert(mandant: MandantMySuffix): MandantMySuffix {
        const copy: MandantMySuffix = Object.assign({}, mandant);
        return copy;
    }
}
