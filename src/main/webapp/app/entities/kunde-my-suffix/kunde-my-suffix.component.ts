import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { KundeMySuffix } from './kunde-my-suffix.model';
import { KundeMySuffixService } from './kunde-my-suffix.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-kunde-my-suffix',
    templateUrl: './kunde-my-suffix.component.html'
})
export class KundeMySuffixComponent implements OnInit, OnDestroy {
kundes: KundeMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private kundeService: KundeMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.kundeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.kundes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInKundes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: KundeMySuffix) {
        return item.id;
    }
    registerChangeInKundes() {
        this.eventSubscriber = this.eventManager.subscribe('kundeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
