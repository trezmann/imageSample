import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { KundeMySuffixComponent } from './kunde-my-suffix.component';
import { KundeMySuffixDetailComponent } from './kunde-my-suffix-detail.component';
import { KundeMySuffixPopupComponent } from './kunde-my-suffix-dialog.component';
import { KundeMySuffixDeletePopupComponent } from './kunde-my-suffix-delete-dialog.component';

export const kundeRoute: Routes = [
    {
        path: 'kunde-my-suffix',
        component: KundeMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Kundes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'kunde-my-suffix/:id',
        component: KundeMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Kundes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const kundePopupRoute: Routes = [
    {
        path: 'kunde-my-suffix-new',
        component: KundeMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Kundes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'kunde-my-suffix/:id/edit',
        component: KundeMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Kundes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'kunde-my-suffix/:id/delete',
        component: KundeMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Kundes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
