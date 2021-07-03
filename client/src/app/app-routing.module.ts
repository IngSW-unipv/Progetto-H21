import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './auth/auth.component';
import { PrebuiltsComponent } from './prebuilts/prebuilts.component';
import { ProductsComponent } from './products/products.component';
import { ListComponent } from './list/list.component';
import { PcComponentsListComponent } from './products/pc-components/pc-components-list/pc-components-list.component';
import { CompatibilityListComponent } from './products/compatibility/compatibility-list.component';
import { AdminPrebuiltsListComponent } from './products/admin-prebuilts/admin-prebuilts-list/admin-prebuilts-list.component';
import { AuthGuard } from './auth/auth.guard';
import { FamiliesListComponent } from './products/families/families-list.component';
import { ComponentsByTypeComponent } from './list/components-by-type/components-by-type.component';
import { EditPcComponentComponent } from './products/pc-components/edit-pc-component/edit-pc-component.component';
import { AddPcComponentComponent } from './products/pc-components/add-pc-component/add-pc-component.component';
import { EditFamilyComponent } from './products/families/edit-family/edit-family.component';
import { AddFamilyComponent } from './products/families/add-family/add-family.component';
import { EditCompatibilityComponent } from './products/compatibility/edit-compatibility/edit-compatibility.component';
import { AddCompatibilityComponent } from './products/compatibility/add-compatibility/add-compatibility.component';
import { EditAdminPrebuiltComponent } from './products/admin-prebuilts/edit-admin-prebuilt/edit-admin-prebuilt.component';
import { AddAdminPrebuiltComponent } from './products/admin-prebuilts/add-admin-prebuilt/add-admin-prebuilt.component';

const appRoutes: Routes = [
  { path: '', redirectTo: '/auth', pathMatch: 'full' },
  { path: 'prebuilts', component: PrebuiltsComponent },
  { path: 'products',
    component: ProductsComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'pc-components',component: PcComponentsListComponent},
      {path: 'admin-prebuilts', component: AdminPrebuiltsListComponent},
      {path: 'family', component: FamiliesListComponent},
      {path: 'compatibility', component: CompatibilityListComponent}
    ]},
  { path: 'auth', component:  AuthComponent},

  { path: 'edit-pc-component/:id', component: EditPcComponentComponent},
  { path: 'add-pc-component', component: AddPcComponentComponent},

  { path: 'edit-family/:id', component: EditFamilyComponent},
  { path: 'add-family', component: AddFamilyComponent},

  { path: 'edit-compatibility/:id', component: EditCompatibilityComponent},
  { path: 'add-compatibility', component: AddCompatibilityComponent},

  { path: 'edit-prebuilt/:id', component: EditAdminPrebuiltComponent},
  { path: 'add-prebuilt', component: AddAdminPrebuiltComponent},

  { path: 'list', component: ListComponent },
  { path: 'list/components-by-type/:id', component: ComponentsByTypeComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes,{ onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
