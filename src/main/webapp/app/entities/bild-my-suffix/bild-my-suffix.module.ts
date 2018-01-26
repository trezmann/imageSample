import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImageSampleSharedModule } from '../../shared';
import {
    BildMySuffixService,
    BildMySuffixPopupService,
    BildMySuffixComponent,
    BildMySuffixDetailComponent,
    BildMySuffixDialogComponent,
    BildMySuffixPopupComponent,
    BildMySuffixDeletePopupComponent,
    BildMySuffixDeleteDialogComponent,
    bildRoute,
    bildPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bildRoute,
    ...bildPopupRoute,
];

@NgModule({
    imports: [
        ImageSampleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BildMySuffixComponent,
        BildMySuffixDetailComponent,
        BildMySuffixDialogComponent,
        BildMySuffixDeleteDialogComponent,
        BildMySuffixPopupComponent,
        BildMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        BildMySuffixComponent,
        BildMySuffixDialogComponent,
        BildMySuffixPopupComponent,
        BildMySuffixDeleteDialogComponent,
        BildMySuffixDeletePopupComponent,
    ],
    providers: [
        BildMySuffixService,
        BildMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ImageSampleBildMySuffixModule {}
