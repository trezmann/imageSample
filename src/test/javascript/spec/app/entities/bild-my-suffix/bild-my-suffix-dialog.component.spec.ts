/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ImageSampleTestModule } from '../../../test.module';
import { BildMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/bild-my-suffix/bild-my-suffix-dialog.component';
import { BildMySuffixService } from '../../../../../../main/webapp/app/entities/bild-my-suffix/bild-my-suffix.service';
import { BildMySuffix } from '../../../../../../main/webapp/app/entities/bild-my-suffix/bild-my-suffix.model';
import { KundeMySuffixService } from '../../../../../../main/webapp/app/entities/kunde-my-suffix';
import { MandantMySuffixService } from '../../../../../../main/webapp/app/entities/mandant-my-suffix';

describe('Component Tests', () => {

    describe('BildMySuffix Management Dialog Component', () => {
        let comp: BildMySuffixDialogComponent;
        let fixture: ComponentFixture<BildMySuffixDialogComponent>;
        let service: BildMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ImageSampleTestModule],
                declarations: [BildMySuffixDialogComponent],
                providers: [
                    KundeMySuffixService,
                    MandantMySuffixService,
                    BildMySuffixService
                ]
            })
            .overrideTemplate(BildMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BildMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BildMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BildMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.bild = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bildListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BildMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.bild = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bildListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
