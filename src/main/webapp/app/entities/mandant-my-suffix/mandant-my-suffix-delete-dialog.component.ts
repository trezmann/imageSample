import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MandantMySuffix } from './mandant-my-suffix.model';
import { MandantMySuffixPopupService } from './mandant-my-suffix-popup.service';
import { MandantMySuffixService } from './mandant-my-suffix.service';

@Component({
    selector: 'jhi-mandant-my-suffix-delete-dialog',
    templateUrl: './mandant-my-suffix-delete-dialog.component.html'
})
export class MandantMySuffixDeleteDialogComponent {

    mandant: MandantMySuffix;

    constructor(
        private mandantService: MandantMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mandantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mandantListModification',
                content: 'Deleted an mandant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mandant-my-suffix-delete-popup',
    template: ''
})
export class MandantMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mandantPopupService: MandantMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mandantPopupService
                .open(MandantMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
