import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MandantMySuffix } from './mandant-my-suffix.model';
import { MandantMySuffixService } from './mandant-my-suffix.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-mandant-my-suffix',
    templateUrl: './mandant-my-suffix.component.html'
})
export class MandantMySuffixComponent implements OnInit, OnDestroy {
mandants: MandantMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private mandantService: MandantMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.mandantService.query().subscribe(
            (res: ResponseWrapper) => {
                this.mandants = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMandants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MandantMySuffix) {
        return item.id;
    }
    registerChangeInMandants() {
        this.eventSubscriber = this.eventManager.subscribe('mandantListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
