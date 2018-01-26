/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ImageSampleTestModule } from '../../../test.module';
import { KundeMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/kunde-my-suffix/kunde-my-suffix-detail.component';
import { KundeMySuffixService } from '../../../../../../main/webapp/app/entities/kunde-my-suffix/kunde-my-suffix.service';
import { KundeMySuffix } from '../../../../../../main/webapp/app/entities/kunde-my-suffix/kunde-my-suffix.model';

describe('Component Tests', () => {

    describe('KundeMySuffix Management Detail Component', () => {
        let comp: KundeMySuffixDetailComponent;
        let fixture: ComponentFixture<KundeMySuffixDetailComponent>;
        let service: KundeMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ImageSampleTestModule],
                declarations: [KundeMySuffixDetailComponent],
                providers: [
                    KundeMySuffixService
                ]
            })
            .overrideTemplate(KundeMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KundeMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KundeMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new KundeMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.kunde).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
