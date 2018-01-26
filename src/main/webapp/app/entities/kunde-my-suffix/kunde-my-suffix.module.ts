import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImageSampleSharedModule } from '../../shared';
import {
    KundeMySuffixService,
    KundeMySuffixPopupService,
    KundeMySuffixComponent,
    KundeMySuffixDetailComponent,
    KundeMySuffixDialogComponent,
    KundeMySuffixPopupComponent,
    KundeMySuffixDeletePopupComponent,
    KundeMySuffixDeleteDialogComponent,
    kundeRoute,
    kundePopupRoute,
} from './';

const ENTITY_STATES = [
    ...kundeRoute,
    ...kundePopupRoute,
];

@NgModule({
    imports: [
        ImageSampleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        KundeMySuffixComponent,
        KundeMySuffixDetailComponent,
        KundeMySuffixDialogComponent,
        KundeMySuffixDeleteDialogComponent,
        KundeMySuffixPopupComponent,
        KundeMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        KundeMySuffixComponent,
        KundeMySuffixDialogComponent,
        KundeMySuffixPopupComponent,
        KundeMySuffixDeleteDialogComponent,
        KundeMySuffixDeletePopupComponent,
    ],
    providers: [
        KundeMySuffixService,
        KundeMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ImageSampleKundeMySuffixModule {}
