import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ComponentType } from '../shared/models/component-type.model';
import { PcComponents } from '../shared/models/pc-components-model';
import { CompatibilityService } from '../shared/services/compatibility.service';
import { ComponentTypeService } from '../shared/services/component-type.service';
import { PcComponentsService } from '../shared/services/pc-components.service';

/**
 * @author Filippo Casarosa
 */
@Injectable({ providedIn: 'root' })
export class ListService {
  private list: PcComponents[];
  private componentTypeList: ComponentType[];
  private current: number;
  private totalPrice: number;
  private totalPower: number;
  private powerSupplied: number;
  public setupState$: Subject<any> = new Subject;
  public initState$: Subject<any> = new Subject;


  constructor(private componentTypeService: ComponentTypeService,
    private compatibilityService: CompatibilityService,
    private pcComponentsService: PcComponentsService) {
    this.initState$.next(false);
    this.initSetup();
    this.componentTypeList = [];
    this.list = [];
    this.current = 0;
    this.totalPrice = 0;
    this.totalPower = 0;
    this.powerSupplied = 0;
  }

  //get current
  public getCurrent(): number{
    return this.current;
  }

  //get list
  public getList(): PcComponents[]{
    return this.list;
  }

  public getTotalPrice(): number{
    this.calculateTotalPrice();
    return this.totalPrice;
  }

  public getTotalPower(): number{
    this.calculateTotalPower();
    return this.totalPower;
  }

  public getPowerSupplied(): number{
    // const regex = RegExp('([^\d]|^)\d{3,4}([^\d]|$)');
    // if(this.list[this.list.length -1].componentFamily.type.sortOrder === this.componentTypeList.length) {
    //   return this.powerSupplied = +regex.exec(this.list[this.list.length -1].name);
    // }else return this.powerSupplied
    return this.powerSupplied;
  }

  private calculateTotalPrice() {
    this.totalPrice += this.list[this.list.length -1].price;
  }

  private calculateTotalPower() {
    if(this.list[this.list.length -1].componentFamily.type.sortOrder === this.componentTypeList.length){
      this.powerSupplied = this.list[this.list.length -1].power;
    } else {
      this.totalPower += this.list[this.list.length -1].power;
    }
  }

  checkPowerSupplied(): boolean{
    const powerSurplus = 1.2;
    return ((this.powerSupplied)<(this.totalPower*powerSurplus));
  }

  // 1. scaricare lista dei tipi
  public fetchComponentTypes(): Observable<ComponentType[]> {
    return of(this.componentTypeList);
  }

  // 2. inizializzare il setup, il setup ha uno stato incompleto e completo
  public initSetup() {
    this.list = [];
    this.setupState$.next(true);
    this.componentTypeService.getComponentType().
      subscribe(data => {
        this.componentTypeList = data;
        this.initState$.next(true);
      }
      );
  }

  // 3. aggiungere il componenti al setup
  public addComponent(component: PcComponents) {
    this.list.push(component);
    if (this.list.length === this.componentTypeList.length) {
      this.setupState$.next(false); // va emesso ad esempio come subject
    }
    this.current = this.list.length;

  }

  //get components of pcComponents
  public getComponents(typeId: number): Observable<PcComponents[]> {
    if (this.list.length === 0) {
      return this.fetchPcComponents(typeId);
    }
    else {
      return this.fetchCompatiblePcComponents();
    }

  }

  //get components of the first type
  private fetchPcComponents(typeId: number): Observable<PcComponents[]> {
    return this.pcComponentsService.getPcComponentsByType(typeId).pipe(
      tap((data) => console.log()
      )
    );
  }

  //get components that are compatible wiht the previous one
  private fetchCompatiblePcComponents(): Observable<PcComponents[]> {
    return this.compatibilityService.getComponentsByCompatibility(this.list[this.list.length -1].id).pipe(
      tap((data) => console.log()
      )
    );
  }

  public deleteLastComponent(){
    this.list.splice(this.list.length -1);
    if (this.list.length === this.componentTypeList.length) {
      this.setupState$.next(false); // va emesso ad esempio come subject
    }
    this.current = this.list.length;
  }

  // 6. esegue lo step 4, aggiunge un componente secondo un algoritmo, e
  // passa al successivo
  public autoComplete() {
    // vedi codice del creator lato server
  }
}
