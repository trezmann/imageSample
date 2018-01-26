import { BaseEntity } from './../../shared';

export class BildMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public bildDateiContentType?: string,
        public bildDatei?: any,
        public bildId?: number,
    ) {
    }
}
