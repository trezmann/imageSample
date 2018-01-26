import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TagMySuffix } from './tag-my-suffix.model';
import { TagMySuffixPopupService } from './tag-my-suffix-popup.service';
import { TagMySuffixService } from './tag-my-suffix.service';
import { BildMySuffix, BildMySuffixService } from '../bild-my-suffix';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-tag-my-suffix-dialog',
    templateUrl: './tag-my-suffix-dialog.component.html'
})
export class TagMySuffixDialogComponent implements OnInit {

    tag: TagMySuffix;
    isSaving: boolean;

    bilds: BildMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private tagService: TagMySuffixService,
        private bildService: BildMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.bildService.query()
            .subscribe((res: ResponseWrapper) => { this.bilds = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tag.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tagService.update(this.tag));
        } else {
            this.subscribeToSaveResponse(
                this.tagService.create(this.tag));
        }
    }

    private subscribeToSaveResponse(result: Observable<TagMySuffix>) {
        result.subscribe((res: TagMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TagMySuffix) {
        this.eventManager.broadcast({ name: 'tagListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBildById(index: number, item: BildMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-tag-my-suffix-popup',
    template: ''
})
export class TagMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tagPopupService: TagMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tagPopupService
                    .open(TagMySuffixDialogComponent as Component, params['id']);
            } else {
                this.tagPopupService
                    .open(TagMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
