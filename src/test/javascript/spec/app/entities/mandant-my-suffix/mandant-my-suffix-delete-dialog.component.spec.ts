/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ImageSampleTestModule } from '../../../test.module';
import { MandantMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/mandant-my-suffix/mandant-my-suffix-delete-dialog.component';
import { MandantMySuffixService } from '../../../../../../main/webapp/app/entities/mandant-my-suffix/mandant-my-suffix.service';

describe('Component Tests', () => {

    describe('MandantMySuffix Management Delete Component', () => {
        let comp: MandantMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<MandantMySuffixDeleteDialogComponent>;
        let service: MandantMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ImageSampleTestModule],
                declarations: [MandantMySuffixDeleteDialogComponent],
                providers: [
                    MandantMySuffixService
                ]
            })
            .overrideTemplate(MandantMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MandantMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MandantMySuffixService);
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
