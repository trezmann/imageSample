import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { KundeMySuffix } from './kunde-my-suffix.model';
import { KundeMySuffixPopupService } from './kunde-my-suffix-popup.service';
import { KundeMySuffixService } from './kunde-my-suffix.service';

@Component({
    selector: 'jhi-kunde-my-suffix-delete-dialog',
    templateUrl: './kunde-my-suffix-delete-dialog.component.html'
})
export class KundeMySuffixDeleteDialogComponent {

    kunde: KundeMySuffix;

    constructor(
        private kundeService: KundeMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.kundeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'kundeListModification',
                content: 'Deleted an kunde'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-kunde-my-suffix-delete-popup',
    template: ''
})
export class KundeMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kundePopupService: KundeMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.kundePopupService
                .open(KundeMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
