import { AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { DialogComponent } from '../shared/dialog/dialog.component';
import { ComponentType } from '../shared/models/component-type.model';
import { PcComponents } from '../shared/models/pc-components-model';
import { ComponentTypeService } from '../shared/services/component-type.service';
import { ListService } from '../shared/services/list.service';

/**
 * @author Filippo Casarosa
 */
@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit, OnDestroy {
  pcComponents: PcComponents [];
  loadedComponentTypes: ComponentType[];
  isFetching: boolean;
  error = null;
  private errorSub: Subscription;
  totalPrice: number;
  totalPower: number;
  powerSupplied: number;
  enableCompleteButton: boolean;
  isAuthenticated: boolean;
  adminSub: Subscription;

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';


  constructor(private componentTypeService: ComponentTypeService,
              private listService: ListService,
              private router: Router,
              private dialog: MatDialog,
              private _snackBar: MatSnackBar,
              private authService: AuthService) {
  this.loadedComponentTypes = [];
  this.pcComponents = [];
  this.isFetching = false;
  this.totalPrice = 0;
  this.totalPower = 0;
  this.powerSupplied = 0;
  this.enableCompleteButton = false;
  this.isAuthenticated = false;
  }

  ngOnInit(): void {
    this.adminSub = this.authService.admin.subscribe(admin => {
      this.isAuthenticated = !!admin
    });
    this.fetchComponentType();
    if(this.listService.getList().length >= 1) {
      this.fetchConfiguration();
    }
  }
  /**
   * Chiude l'observable che ha la funzione di mostrare l'errore
   * @author Filippo Casarosa
   */
  ngOnDestroy(): void {
    this.errorSub.unsubscribe;
    this.adminSub.unsubscribe;
  }

  /**
   * Resetta l'errore a null
   * @author Filippo Casarosa
   */
  onHandleError(){
    this.error = null;
  }

  /**
   * Recupera tutti i ComponentType
   * @author Filippo Casarosa
   */
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
        this.loadedComponentTypes = componentType.map((current,index) => {
          return {
            ...current,
            active: index === this.listService.getCurrent()
          }
        })
        console.log('Component Types');
        console.log(this.loadedComponentTypes);
        if(this.pcComponents.length === this.loadedComponentTypes.length){
          this.enableCompleteButton = true;
        }else{
          this.enableCompleteButton = false;
        }
      },
      error => {
        this.isFetching = false;
        this.error = error.message;
      }
    );
  }

  /**
   * Recupera la configurazione
   * @author Filippo Casarosa
   */
  fetchConfiguration(){
    this.pcComponents = this.listService.getList();
    console.log('pcComponents');
    console.log(this.pcComponents);
    this.totalPrice = this.listService.getTotalPrice();
    console.log('Get total price');
    console.log(this.totalPrice);
    this.totalPower =this.listService.getTotalPower();
    console.log('TotalPower');
    console.log(this.totalPower);
    this.powerSupplied = this.listService.getPowerSupplied();
    console.log('Power Supplied');
    console.log(this.powerSupplied);
  }


  /**
   * conferma Configurazione
   * @author Filippo Casarosa
   */
  confirmConfig(){
    if(this.listService.checkPowerSupplied()){
      this.openSnackBar();
      this.listService.deleteLastComponent();
      this.fetchComponentType();
    } else{
      const dialogRef = this.dialog.open(DialogComponent, {
        width: '330px',
      });
      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });
    }
  }

  completeConfiguration(){
    this.listService.autoConfig(this.pcComponents[this.pcComponents.length -1].componentFamily.type.id);
  }

  // public autoConfig(typeId: number) {
  //   let choosenComponent: PcComponents
  //   let filteredPcComponents: PcComponents[]
  //   this.listService.getComponents(typeId).subscribe(res => {
  //     filteredPcComponents = res
  //     choosenComponent = filteredPcComponents[Math.floor(Math.random() * filteredPcComponents.length)];
  //     this.listService.addComponent(choosenComponent);
  //     if (this.pcComponents.length != this.loadedComponentTypes.length) {
  //       this.autoConfig(choosenComponent.componentFamily.type.id);
  //     }
  //   });
  // }

  /**
   * apre SnackBar per errore potenza
   * @author Filippo Casarosa
   */
  openSnackBar(){
    this._snackBar.open('Potenza erogata non sufficiente', 'ok', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }
}
