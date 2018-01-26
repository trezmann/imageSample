import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { BildMySuffix } from './bild-my-suffix.model';
import { BildMySuffixPopupService } from './bild-my-suffix-popup.service';
import { BildMySuffixService } from './bild-my-suffix.service';
import { KundeMySuffix, KundeMySuffixService } from '../kunde-my-suffix';
import { MandantMySuffix, MandantMySuffixService } from '../mandant-my-suffix';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-bild-my-suffix-dialog',
    templateUrl: './bild-my-suffix-dialog.component.html'
})
export class BildMySuffixDialogComponent implements OnInit {

    bild: BildMySuffix;
    isSaving: boolean;

    kundes: KundeMySuffix[];

    mandants: MandantMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private bildService: BildMySuffixService,
        private kundeService: KundeMySuffixService,
        private mandantService: MandantMySuffixService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.kundeService.query()
            .subscribe((res: ResponseWrapper) => { this.kundes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.mandantService.query()
            .subscribe((res: ResponseWrapper) => { this.mandants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.bild, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bild.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bildService.update(this.bild));
        } else {
            this.subscribeToSaveResponse(
                this.bildService.create(this.bild));
        }
    }

    private subscribeToSaveResponse(result: Observable<BildMySuffix>) {
        result.subscribe((res: BildMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: BildMySuffix) {
        this.eventManager.broadcast({ name: 'bildListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackKundeById(index: number, item: KundeMySuffix) {
        return item.id;
    }

    trackMandantById(index: number, item: MandantMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-bild-my-suffix-popup',
    template: ''
})
export class BildMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bildPopupService: BildMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bildPopupService
                    .open(BildMySuffixDialogComponent as Component, params['id']);
            } else {
                this.bildPopupService
                    .open(BildMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
