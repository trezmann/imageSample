import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BildMySuffixComponent } from './bild-my-suffix.component';
import { BildMySuffixDetailComponent } from './bild-my-suffix-detail.component';
import { BildMySuffixPopupComponent } from './bild-my-suffix-dialog.component';
import { BildMySuffixDeletePopupComponent } from './bild-my-suffix-delete-dialog.component';

export const bildRoute: Routes = [
    {
        path: 'bild-my-suffix',
        component: BildMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bilds'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bild-my-suffix/:id',
        component: BildMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bilds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bildPopupRoute: Routes = [
    {
        path: 'bild-my-suffix-new',
        component: BildMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bilds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bild-my-suffix/:id/edit',
        component: BildMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bilds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bild-my-suffix/:id/delete',
        component: BildMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bilds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
