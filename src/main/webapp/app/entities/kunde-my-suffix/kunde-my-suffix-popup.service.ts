import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { KundeMySuffix } from './kunde-my-suffix.model';
import { KundeMySuffixService } from './kunde-my-suffix.service';

@Injectable()
export class KundeMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private kundeService: KundeMySuffixService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.kundeService.find(id).subscribe((kunde) => {
                    if (kunde.sDatum) {
                        kunde.sDatum = {
                            year: kunde.sDatum.getFullYear(),
                            month: kunde.sDatum.getMonth() + 1,
                            day: kunde.sDatum.getDate()
                        };
                    }
                    if (kunde.eDatum) {
                        kunde.eDatum = {
                            year: kunde.eDatum.getFullYear(),
                            month: kunde.eDatum.getMonth() + 1,
                            day: kunde.eDatum.getDate()
                        };
                    }
                    this.ngbModalRef = this.kundeModalRef(component, kunde);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.kundeModalRef(component, new KundeMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    kundeModalRef(component: Component, kunde: KundeMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.kunde = kunde;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
