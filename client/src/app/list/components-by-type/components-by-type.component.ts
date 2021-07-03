import { AfterViewInit, Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { ComponentType } from 'src/app/shared/models/component-type.model';
import { PcComponents } from 'src/app/shared/models/pc-components-model';
import { CompatibilityService } from 'src/app/shared/services/compatibility.service';
import { ListService } from 'src/app/list/list.service';
import { PcComponentsService } from 'src/app/shared/services/pc-components.service';

/**
 * @author Filippo Casarosa
 */
@Component({
  selector: 'app-componens-by-type',
  templateUrl: './components-by-type.component.html',
  styleUrls: ['./components-by-type.component.scss']
})
export class ComponentsByTypeComponent implements OnInit, AfterViewInit {
  loadedPcComponentsbyType: PcComponents[] = [];
  someObservable$: any;
  componentType: ComponentType;
  isFetching = false;
  error = null;
  list: PcComponents[];
  private errorSub: Subscription;
  dataSource: MatTableDataSource<PcComponents>;
  displayedColumns: string[] = ['name', 'price', 'family', 'actions'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;


  constructor(private pcComponentsService: PcComponentsService,
              private listService: ListService,
              private compatibilityService: CompatibilityService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource();
    this.list = this.listService.getList();
    this.route.fragment.subscribe();
    const typeId = +this.route.snapshot.params['id'];
    this.listService.getComponents(typeId).subscribe(res => this.consume(res), error => this.manageError(error));
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
  /**
   * Chiude l'observable che ha la funzione di mostrare l'errore
   * @author Filippo Casarosa
   */
  // ngOnDestroy(): void {
  //   this.errorSub.unsubscribe;
  // }

  /**
   * Resetta l'errore a null
   * @author Filippo Casarosa
   */
  onHandleError(){
    this.error = null;
  }

  /**
   * Filtra i componenti per nome
   * @param filterValue Il filtro da applicare (nel nostro caso una stringa)
   * @author Filippo Casarosa
   */
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  private consume(pcComponents: PcComponents[]){
      this.isFetching = false;
      this.loadedPcComponentsbyType = pcComponents;
      this.dataSource.data = this.loadedPcComponentsbyType;
  }

  private manageError(error: any){
    this.isFetching = false;
    this.error = error.message;
  }

  /**
   * Passa il componente selezionato a List
   * @param pcComponent Il componente da passare
   * @author Filippo Casarosa
   * @author Alessandro Terracciano
   */
  addPcComponent(pcComponent: PcComponents) {
    this.listService.addComponent(pcComponent);
    this.router.navigate(['list']);
  }
}
