/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ImageSampleTestModule } from '../../../test.module';
import { BildMySuffixComponent } from '../../../../../../main/webapp/app/entities/bild-my-suffix/bild-my-suffix.component';
import { BildMySuffixService } from '../../../../../../main/webapp/app/entities/bild-my-suffix/bild-my-suffix.service';
import { BildMySuffix } from '../../../../../../main/webapp/app/entities/bild-my-suffix/bild-my-suffix.model';

describe('Component Tests', () => {

    describe('BildMySuffix Management Component', () => {
        let comp: BildMySuffixComponent;
        let fixture: ComponentFixture<BildMySuffixComponent>;
        let service: BildMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ImageSampleTestModule],
                declarations: [BildMySuffixComponent],
                providers: [
                    BildMySuffixService
                ]
            })
            .overrideTemplate(BildMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BildMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BildMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new BildMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.bilds[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
