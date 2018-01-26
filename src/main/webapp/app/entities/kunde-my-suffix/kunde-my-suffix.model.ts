import { BaseEntity } from './../../shared';

export class KundeMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public stammnummer?: string,
        public sDatum?: any,
        public eDatum?: any,
        public steuernummer?: string,
        public kundeId?: number,
    ) {
    }
}
