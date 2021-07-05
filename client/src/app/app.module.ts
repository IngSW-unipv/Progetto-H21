import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AngularMaterialModule } from './modules/angualr-material/angular-material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ProductsComponent } from './products/products.component';
import { HeaderComponent } from './header/header.component';
import { AuthComponent } from './auth/auth.component';
import { PrebuiltsComponent } from './prebuilts/prebuilts.component';
import { PrebuiltsFilterComponent } from './prebuilts/prebuilts-filter/prebuilts-filter.component';
import { PrebuiltsListComponent } from './prebuilts/prebuilts-list/prebuilts-list.component';
import { ListComponent } from './list/list.component';
import { PcComponentsListComponent } from './products/pc-components/pc-components-list/pc-components-list.component';
import { DialogComponent } from './shared/dialog/dialog.component';
import { AdminPrebuiltsListComponent } from './products/admin-prebuilts/admin-prebuilts-list/admin-prebuilts-list.component';
import { CompatibilityListComponent } from './products/compatibility/compatibility-list.component'
import { LoadingSpinner } from './shared/loading-spinner/loading-spinner.component';
import { FamiliesListComponent } from './products/families/families-list.component';
import { ComponentsByTypeComponent } from './list/components-by-type/components-by-type.component';

import { AuthInterceptorService } from './auth/auth-interceptor.service';
import { EditPcComponentComponent } from './products/pc-components/edit-pc-component/edit-pc-component.component';
import { AddPcComponentComponent } from './products/pc-components/add-pc-component/add-pc-component.component';
import { EditFamilyComponent } from './products/families/edit-family/edit-family.component';
import { AddFamilyComponent } from './products/families/add-family/add-family.component';
import { AddCompatibilityComponent } from './products/compatibility/add-compatibility/add-compatibility.component';
import { EditCompatibilityComponent } from './products/compatibility/edit-compatibility/edit-compatibility.component';
import { EditAdminPrebuiltComponent } from './products/admin-prebuilts/edit-admin-prebuilt/edit-admin-prebuilt.component';
import { AddAdminPrebuiltComponent } from './products/admin-prebuilts/add-admin-prebuilt/add-admin-prebuilt.component';
import { DetailPrebuiltComponent } from './prebuilts/detail-prebuilt/detail-prebuilt.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    AuthComponent,
    LoadingSpinner,

    PrebuiltsComponent,
    PrebuiltsFilterComponent,
    PrebuiltsListComponent,

    ListComponent,

    ProductsComponent,
      PcComponentsListComponent,
      EditPcComponentComponent,

      AdminPrebuiltsListComponent,

      CompatibilityListComponent,

      FamiliesListComponent,

    DialogComponent,
    ComponentsByTypeComponent,
    AddPcComponentComponent,
    EditFamilyComponent,
    AddFamilyComponent,
    AddCompatibilityComponent,
    EditCompatibilityComponent,
    EditAdminPrebuiltComponent,
    AddAdminPrebuiltComponent,
    DetailPrebuiltComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    AngularMaterialModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
