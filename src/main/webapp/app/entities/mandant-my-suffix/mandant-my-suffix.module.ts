import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImageSampleSharedModule } from '../../shared';
import {
    MandantMySuffixService,
    MandantMySuffixPopupService,
    MandantMySuffixComponent,
    MandantMySuffixDetailComponent,
    MandantMySuffixDialogComponent,
    MandantMySuffixPopupComponent,
    MandantMySuffixDeletePopupComponent,
    MandantMySuffixDeleteDialogComponent,
    mandantRoute,
    mandantPopupRoute,
} from './';

const ENTITY_STATES = [
    ...mandantRoute,
    ...mandantPopupRoute,
];

@NgModule({
    imports: [
        ImageSampleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MandantMySuffixComponent,
        MandantMySuffixDetailComponent,
        MandantMySuffixDialogComponent,
        MandantMySuffixDeleteDialogComponent,
        MandantMySuffixPopupComponent,
        MandantMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        MandantMySuffixComponent,
        MandantMySuffixDialogComponent,
        MandantMySuffixPopupComponent,
        MandantMySuffixDeleteDialogComponent,
        MandantMySuffixDeletePopupComponent,
    ],
    providers: [
        MandantMySuffixService,
        MandantMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ImageSampleMandantMySuffixModule {}
