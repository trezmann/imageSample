import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { MandantMySuffix } from './mandant-my-suffix.model';
import { MandantMySuffixService } from './mandant-my-suffix.service';

@Component({
    selector: 'jhi-mandant-my-suffix-detail',
    templateUrl: './mandant-my-suffix-detail.component.html'
})
export class MandantMySuffixDetailComponent implements OnInit, OnDestroy {

    mandant: MandantMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mandantService: MandantMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMandants();
    }

    load(id) {
        this.mandantService.find(id).subscribe((mandant) => {
            this.mandant = mandant;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMandants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mandantListModification',
            (response) => this.load(this.mandant.id)
        );
    }
}
