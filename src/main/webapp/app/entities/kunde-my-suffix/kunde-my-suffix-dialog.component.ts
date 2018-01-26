import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { KundeMySuffix } from './kunde-my-suffix.model';
import { KundeMySuffixPopupService } from './kunde-my-suffix-popup.service';
import { KundeMySuffixService } from './kunde-my-suffix.service';
import { MandantMySuffix, MandantMySuffixService } from '../mandant-my-suffix';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-kunde-my-suffix-dialog',
    templateUrl: './kunde-my-suffix-dialog.component.html'
})
export class KundeMySuffixDialogComponent implements OnInit {

    kunde: KundeMySuffix;
    isSaving: boolean;

    mandants: MandantMySuffix[];
    sDatumDp: any;
    eDatumDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private kundeService: KundeMySuffixService,
        private mandantService: MandantMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.mandantService.query()
            .subscribe((res: ResponseWrapper) => { this.mandants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.kunde.id !== undefined) {
            this.subscribeToSaveResponse(
                this.kundeService.update(this.kunde));
        } else {
            this.subscribeToSaveResponse(
                this.kundeService.create(this.kunde));
        }
    }

    private subscribeToSaveResponse(result: Observable<KundeMySuffix>) {
        result.subscribe((res: KundeMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: KundeMySuffix) {
        this.eventManager.broadcast({ name: 'kundeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMandantById(index: number, item: MandantMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-kunde-my-suffix-popup',
    template: ''
})
export class KundeMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kundePopupService: KundeMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.kundePopupService
                    .open(KundeMySuffixDialogComponent as Component, params['id']);
            } else {
                this.kundePopupService
                    .open(KundeMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
