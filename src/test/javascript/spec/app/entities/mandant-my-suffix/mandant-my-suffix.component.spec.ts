/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ImageSampleTestModule } from '../../../test.module';
import { MandantMySuffixComponent } from '../../../../../../main/webapp/app/entities/mandant-my-suffix/mandant-my-suffix.component';
import { MandantMySuffixService } from '../../../../../../main/webapp/app/entities/mandant-my-suffix/mandant-my-suffix.service';
import { MandantMySuffix } from '../../../../../../main/webapp/app/entities/mandant-my-suffix/mandant-my-suffix.model';

describe('Component Tests', () => {

    describe('MandantMySuffix Management Component', () => {
        let comp: MandantMySuffixComponent;
        let fixture: ComponentFixture<MandantMySuffixComponent>;
        let service: MandantMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ImageSampleTestModule],
                declarations: [MandantMySuffixComponent],
                providers: [
                    MandantMySuffixService
                ]
            })
            .overrideTemplate(MandantMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MandantMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MandantMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new MandantMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.mandants[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
