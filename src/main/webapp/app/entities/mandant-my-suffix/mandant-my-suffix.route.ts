import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MandantMySuffixComponent } from './mandant-my-suffix.component';
import { MandantMySuffixDetailComponent } from './mandant-my-suffix-detail.component';
import { MandantMySuffixPopupComponent } from './mandant-my-suffix-dialog.component';
import { MandantMySuffixDeletePopupComponent } from './mandant-my-suffix-delete-dialog.component';

export const mandantRoute: Routes = [
    {
        path: 'mandant-my-suffix',
        component: MandantMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mandants'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mandant-my-suffix/:id',
        component: MandantMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mandants'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mandantPopupRoute: Routes = [
    {
        path: 'mandant-my-suffix-new',
        component: MandantMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mandants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mandant-my-suffix/:id/edit',
        component: MandantMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mandants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mandant-my-suffix/:id/delete',
        component: MandantMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mandants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
