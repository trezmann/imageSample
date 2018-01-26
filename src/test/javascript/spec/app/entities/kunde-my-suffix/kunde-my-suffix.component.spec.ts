/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ImageSampleTestModule } from '../../../test.module';
import { KundeMySuffixComponent } from '../../../../../../main/webapp/app/entities/kunde-my-suffix/kunde-my-suffix.component';
import { KundeMySuffixService } from '../../../../../../main/webapp/app/entities/kunde-my-suffix/kunde-my-suffix.service';
import { KundeMySuffix } from '../../../../../../main/webapp/app/entities/kunde-my-suffix/kunde-my-suffix.model';

describe('Component Tests', () => {

    describe('KundeMySuffix Management Component', () => {
        let comp: KundeMySuffixComponent;
        let fixture: ComponentFixture<KundeMySuffixComponent>;
        let service: KundeMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ImageSampleTestModule],
                declarations: [KundeMySuffixComponent],
                providers: [
                    KundeMySuffixService
                ]
            })
            .overrideTemplate(KundeMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KundeMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KundeMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new KundeMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.kundes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
