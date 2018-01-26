/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ImageSampleTestModule } from '../../../test.module';
import { MandantMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/mandant-my-suffix/mandant-my-suffix-dialog.component';
import { MandantMySuffixService } from '../../../../../../main/webapp/app/entities/mandant-my-suffix/mandant-my-suffix.service';
import { MandantMySuffix } from '../../../../../../main/webapp/app/entities/mandant-my-suffix/mandant-my-suffix.model';

describe('Component Tests', () => {

    describe('MandantMySuffix Management Dialog Component', () => {
        let comp: MandantMySuffixDialogComponent;
        let fixture: ComponentFixture<MandantMySuffixDialogComponent>;
        let service: MandantMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ImageSampleTestModule],
                declarations: [MandantMySuffixDialogComponent],
                providers: [
                    MandantMySuffixService
                ]
            })
            .overrideTemplate(MandantMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MandantMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MandantMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MandantMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.mandant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'mandantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MandantMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.mandant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'mandantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
