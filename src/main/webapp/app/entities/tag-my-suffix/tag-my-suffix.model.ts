import { BaseEntity } from './../../shared';

export class TagMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public tag?: string,
        public tagId?: number,
    ) {
    }
}
