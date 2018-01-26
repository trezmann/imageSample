import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ImageSampleMandantMySuffixModule } from './mandant-my-suffix/mandant-my-suffix.module';
import { ImageSampleKundeMySuffixModule } from './kunde-my-suffix/kunde-my-suffix.module';
import { ImageSampleBildMySuffixModule } from './bild-my-suffix/bild-my-suffix.module';
import { ImageSampleTagMySuffixModule } from './tag-my-suffix/tag-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ImageSampleMandantMySuffixModule,
        ImageSampleKundeMySuffixModule,
        ImageSampleBildMySuffixModule,
        ImageSampleTagMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ImageSampleEntityModule {}
