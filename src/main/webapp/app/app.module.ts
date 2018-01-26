import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';

import { ImageSampleSharedModule, UserRouteAccessService } from './shared';
import { ImageSampleAppRoutingModule} from './app-routing.module';
import { ImageSampleHomeModule } from './home/home.module';
import { ImageSampleAdminModule } from './admin/admin.module';
import { ImageSampleAccountModule } from './account/account.module';
import { ImageSampleEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        ImageSampleAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        ImageSampleSharedModule,
        ImageSampleHomeModule,
        ImageSampleAdminModule,
        ImageSampleAccountModule,
        ImageSampleEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class ImageSampleAppModule {}
