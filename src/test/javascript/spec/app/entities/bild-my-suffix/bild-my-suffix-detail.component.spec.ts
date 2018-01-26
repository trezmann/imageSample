/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ImageSampleTestModule } from '../../../test.module';
import { BildMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/bild-my-suffix/bild-my-suffix-detail.component';
import { BildMySuffixService } from '../../../../../../main/webapp/app/entities/bild-my-suffix/bild-my-suffix.service';
import { BildMySuffix } from '../../../../../../main/webapp/app/entities/bild-my-suffix/bild-my-suffix.model';

describe('Component Tests', () => {

    describe('BildMySuffix Management Detail Component', () => {
        let comp: BildMySuffixDetailComponent;
        let fixture: ComponentFixture<BildMySuffixDetailComponent>;
        let service: BildMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ImageSampleTestModule],
                declarations: [BildMySuffixDetailComponent],
                providers: [
                    BildMySuffixService
                ]
            })
            .overrideTemplate(BildMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BildMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BildMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new BildMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.bild).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
