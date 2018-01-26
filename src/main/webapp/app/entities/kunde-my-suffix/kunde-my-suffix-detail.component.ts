import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { KundeMySuffix } from './kunde-my-suffix.model';
import { KundeMySuffixService } from './kunde-my-suffix.service';

@Component({
    selector: 'jhi-kunde-my-suffix-detail',
    templateUrl: './kunde-my-suffix-detail.component.html'
})
export class KundeMySuffixDetailComponent implements OnInit, OnDestroy {

    kunde: KundeMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private kundeService: KundeMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKundes();
    }

    load(id) {
        this.kundeService.find(id).subscribe((kunde) => {
            this.kunde = kunde;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKundes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'kundeListModification',
            (response) => this.load(this.kunde.id)
        );
    }
}
