import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MandantMySuffix } from './mandant-my-suffix.model';
import { MandantMySuffixPopupService } from './mandant-my-suffix-popup.service';
import { MandantMySuffixService } from './mandant-my-suffix.service';

@Component({
    selector: 'jhi-mandant-my-suffix-dialog',
    templateUrl: './mandant-my-suffix-dialog.component.html'
})
export class MandantMySuffixDialogComponent implements OnInit {

    mandant: MandantMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private mandantService: MandantMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mandant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mandantService.update(this.mandant));
        } else {
            this.subscribeToSaveResponse(
                this.mandantService.create(this.mandant));
        }
    }

    private subscribeToSaveResponse(result: Observable<MandantMySuffix>) {
        result.subscribe((res: MandantMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MandantMySuffix) {
        this.eventManager.broadcast({ name: 'mandantListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-mandant-my-suffix-popup',
    template: ''
})
export class MandantMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mandantPopupService: MandantMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mandantPopupService
                    .open(MandantMySuffixDialogComponent as Component, params['id']);
            } else {
                this.mandantPopupService
                    .open(MandantMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
