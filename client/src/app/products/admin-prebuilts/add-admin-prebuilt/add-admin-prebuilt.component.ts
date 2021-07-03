import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ComponentType } from 'src/app/shared/models/component-type.model';
import { PcComponents } from 'src/app/shared/models/pc-components-model';
import { Prebuilt } from 'src/app/shared/models/prebuilt.model';
import { ComponentTypeService } from 'src/app/shared/services/component-type.service';
import { PcComponentsService } from 'src/app/shared/services/pc-components.service';
import { PrebuiltService } from 'src/app/shared/services/prebuilt.service';
/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Component({
  selector: 'app-add-admin-prebuilt',
  templateUrl: './add-admin-prebuilt.component.html',
  styleUrls: ['./add-admin-prebuilt.component.scss']
})
export class AddAdminPrebuiltComponent implements OnInit, OnDestroy {
  newPrebuilt: Prebuilt = new Prebuilt;
  error = null;
  isFetching = false;
  loadedPcComponents: PcComponents[] = [];
  loadedPcComponent: PcComponents;
  private errorSub: Subscription;
  loadedComponentTypes: ComponentType[] = [];
  displayedColumns: string[] = ['componentType','componentId', "button"];
  dataSource : MatTableDataSource<ComponentType>;

  @ViewChild('f', { static: false }) updateForm: NgForm;

  constructor(private prebuiltService: PrebuiltService,
              private componentTypeService: ComponentTypeService,
              private pcComponetsService: PcComponentsService,
              private router: Router) {}

  ngOnInit(): void {
      this.dataSource = new MatTableDataSource();
      this.fetchComponentType();
      this.fetchPcComponents()
  }

  ngOnDestroy() {
    this.errorSub.unsubscribe;
  }

  fetchComponentType(){
    this.errorSub = this.componentTypeService.error.subscribe(
      errorMessage => {
        this.error = errorMessage;
      }
    );
    this.isFetching = true;
    this.componentTypeService.getComponentType().subscribe(
      componentType => {
        this.isFetching = false;
        this.loadedComponentTypes = componentType;
        console.log(this.loadedComponentTypes);
        console.log('Component Types');
        this.dataSource.data = this.loadedComponentTypes;
      },
      error => {
        this.isFetching = false;
        this.error = error.message;
      }
    );
  }

  fetchPcComponents(){
    this.errorSub = this.componentTypeService.error.subscribe(
      errorMessage => {
        this.error = errorMessage;
      }
    );
    this.isFetching = true;
    this.pcComponetsService.getPcComponents().subscribe(
      pcComponents => {
        this.isFetching = false;
        this.loadedPcComponents = pcComponents;
        console.log(this.loadedPcComponents);
        console.log('pcComponents');
      },
      error => {
        this.isFetching = false;
        this.error = error.message;
      }
    );
  }

  onAddComponentToPrebuilt() {
    this.newPrebuilt.pcComponentsId.push(this.updateForm.value.componentId);
    console.log(this.newPrebuilt.pcComponentsId);
    console.log('array pcComponentIds');
  // this.pcComponetsService.getPcComponent(this.updateForm.value.componentId).subscribe(
  //     pcComponent => {
  //       this.newPrebuilt.pcComponents.push(pcComponent);
  //       console.log(this.loadedPcComponent);
  //       console.log('pcComponent');
  //     }
  //   );
  }

  onAddPrebuilt() {
    this.newPrebuilt.name = this.updateForm.value.name;
    this.newPrebuilt.use = this.updateForm.value.use;
    this.newPrebuilt.price = this.updateForm.value.price;
    this.prebuiltService.addPrebuilt(this.newPrebuilt).subscribe();
    this.router.navigate(['/products/admin-prebuilts']);
  }
}
