import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Prebuilt } from 'src/app/shared/models/prebuilt.model';
import { PrebuiltService } from 'src/app/shared/services/prebuilt.service';

/**
 * @author Filippo Casarosa
 */
@Component({
  selector: 'app-admin-prebuilts-list',
  templateUrl: './admin-prebuilts-list.component.html',
  styleUrls: ['./admin-prebuilts-list.component.scss']
})
export class AdminPrebuiltsListComponent implements OnInit, AfterViewInit {
  loadedPrebuilts: Prebuilt[] = [];
  isFetching = false;
  error = null;
  private errorSub: Subscription;
  dataSource: MatTableDataSource<Prebuilt>;
  displayedColumns: string[] = ['name', 'price', 'use', 'actions'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private prebuiltService: PrebuiltService,
              private router: Router) { }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource();
    this.fetchPrebuilts();

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

  fetchPrebuilts(){
    this.errorSub = this.prebuiltService.error.subscribe(
      errorMessage => {
        this.error = errorMessage;
      }
    );
    this.isFetching = true;
    this.prebuiltService.getPrebuilts().subscribe(
      prebuilts => {
        this.isFetching = false;
        this.loadedPrebuilts = prebuilts;
        console.log(this.loadedPrebuilts);
        console.log('Prebuilts');
        this.dataSource.data = this.loadedPrebuilts;
      },
      error => {
        this.isFetching = false;
        this.error = error.message;
      }
    );
  }

  delPrebuilt(selectedPrebuiltId: number) {
    console.log(selectedPrebuiltId);
    this.prebuiltService
      .deletePrebuilt(selectedPrebuiltId)
      .subscribe();
    window.location.reload();
  }
}
