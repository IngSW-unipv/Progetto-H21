import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { PcComponents } from 'src/app/shared/models/pc-components-model';
import { PcComponentsService } from 'src/app/shared/services/pc-components.service';



/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Component({
  selector: 'app-pc-components-list',
  templateUrl: './pc-components-list.component.html',
  styleUrls: ['./pc-components-list.component.scss']
})
export class PcComponentsListComponent implements OnInit, OnDestroy, AfterViewInit{
  loadedPcComponents: PcComponents[] = [];
  isFetching = false;
  error = null;
  private errorSub: Subscription;
  dataSource: MatTableDataSource<PcComponents>;
  displayedColumns: string[] = ['name', 'price', 'family', 'actions'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private pcComponentsService: PcComponentsService,
              private router: Router) { }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource();
    this.fetchPcComponent();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  //chiude l'observable che ha la funzione di mostrare l'errore
  ngOnDestroy(): void {
    this.errorSub.unsubscribe;
  }

  //risetta l'errore a null
  onHandleError(){
    this.error = null;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  fetchPcComponent(){
    this.errorSub = this.pcComponentsService.error.subscribe(
      errorMessage => {
        this.error = errorMessage;
      }
    );
    this.isFetching = true;
    this.pcComponentsService.getPcComponents().subscribe(
      pcComponents => {
        this.isFetching = false;
        this.loadedPcComponents = pcComponents;
        console.log(this.loadedPcComponents);
        console.log('Pc Components');
        this.dataSource.data = this.loadedPcComponents;
      },
      error => {
        this.isFetching = false;
        this.error = error.message;
      }
    );
  }

  deletePcComponent(selectedProductId: number) {
    console.log(selectedProductId);
    this.pcComponentsService
      .deletePcComponent(selectedProductId)
      .subscribe();
    window.location.reload();
  }
}
