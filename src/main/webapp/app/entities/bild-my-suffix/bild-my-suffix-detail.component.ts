import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { BildMySuffix } from './bild-my-suffix.model';
import { BildMySuffixService } from './bild-my-suffix.service';

@Component({
    selector: 'jhi-bild-my-suffix-detail',
    templateUrl: './bild-my-suffix-detail.component.html'
})
export class BildMySuffixDetailComponent implements OnInit, OnDestroy {

    bild: BildMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private bildService: BildMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBilds();
    }

    load(id) {
        this.bildService.find(id).subscribe((bild) => {
            this.bild = bild;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBilds() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bildListModification',
            (response) => this.load(this.bild.id)
        );
    }
}
