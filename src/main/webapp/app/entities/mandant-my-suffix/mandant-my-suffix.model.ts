import { BaseEntity } from './../../shared';

export class MandantMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public kurzbez?: string,
        public bezeichnung?: string,
        public aktiv?: boolean,
    ) {
        this.aktiv = false;
    }
}
