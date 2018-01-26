/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ImageSampleTestModule } from '../../../test.module';
import { KundeMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/kunde-my-suffix/kunde-my-suffix-dialog.component';
import { KundeMySuffixService } from '../../../../../../main/webapp/app/entities/kunde-my-suffix/kunde-my-suffix.service';
import { KundeMySuffix } from '../../../../../../main/webapp/app/entities/kunde-my-suffix/kunde-my-suffix.model';
import { MandantMySuffixService } from '../../../../../../main/webapp/app/entities/mandant-my-suffix';

describe('Component Tests', () => {

    describe('KundeMySuffix Management Dialog Component', () => {
        let comp: KundeMySuffixDialogComponent;
        let fixture: ComponentFixture<KundeMySuffixDialogComponent>;
        let service: KundeMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ImageSampleTestModule],
                declarations: [KundeMySuffixDialogComponent],
                providers: [
                    MandantMySuffixService,
                    KundeMySuffixService
                ]
            })
            .overrideTemplate(KundeMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KundeMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KundeMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new KundeMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.kunde = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'kundeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new KundeMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.kunde = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'kundeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
