import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BildMySuffix } from './bild-my-suffix.model';
import { BildMySuffixPopupService } from './bild-my-suffix-popup.service';
import { BildMySuffixService } from './bild-my-suffix.service';

@Component({
    selector: 'jhi-bild-my-suffix-delete-dialog',
    templateUrl: './bild-my-suffix-delete-dialog.component.html'
})
export class BildMySuffixDeleteDialogComponent {

    bild: BildMySuffix;

    constructor(
        private bildService: BildMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bildService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bildListModification',
                content: 'Deleted an bild'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bild-my-suffix-delete-popup',
    template: ''
})
export class BildMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bildPopupService: BildMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bildPopupService
                .open(BildMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
