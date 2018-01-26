/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ImageSampleTestModule } from '../../../test.module';
import { BildMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/bild-my-suffix/bild-my-suffix-delete-dialog.component';
import { BildMySuffixService } from '../../../../../../main/webapp/app/entities/bild-my-suffix/bild-my-suffix.service';

describe('Component Tests', () => {

    describe('BildMySuffix Management Delete Component', () => {
        let comp: BildMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<BildMySuffixDeleteDialogComponent>;
        let service: BildMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ImageSampleTestModule],
                declarations: [BildMySuffixDeleteDialogComponent],
                providers: [
                    BildMySuffixService
                ]
            })
            .overrideTemplate(BildMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BildMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BildMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
