/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ImageSampleTestModule } from '../../../test.module';
import { MandantMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/mandant-my-suffix/mandant-my-suffix-detail.component';
import { MandantMySuffixService } from '../../../../../../main/webapp/app/entities/mandant-my-suffix/mandant-my-suffix.service';
import { MandantMySuffix } from '../../../../../../main/webapp/app/entities/mandant-my-suffix/mandant-my-suffix.model';

describe('Component Tests', () => {

    describe('MandantMySuffix Management Detail Component', () => {
        let comp: MandantMySuffixDetailComponent;
        let fixture: ComponentFixture<MandantMySuffixDetailComponent>;
        let service: MandantMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ImageSampleTestModule],
                declarations: [MandantMySuffixDetailComponent],
                providers: [
                    MandantMySuffixService
                ]
            })
            .overrideTemplate(MandantMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MandantMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MandantMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new MandantMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.mandant).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
