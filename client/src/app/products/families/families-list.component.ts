import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Family } from '../../shared/models/family.model';
import { FamilyService } from '../../shared/services/family.service';

/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Component({
  selector: 'app-families-list',
  templateUrl: './families-list.component.html',
  styleUrls: ['./families-list.component.scss']
})
export class FamiliesListComponent implements OnInit, OnDestroy, AfterViewInit{
  loadedFamilies: Family[] = [];
  isFetching = false;
  error = null;
  private errorSub: Subscription;
  dataSource: MatTableDataSource<Family>;
  displayedColumns: string[] = ['name', 'actions'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private familyService: FamilyService,
              private router: Router) { }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource();
    this.fetchFamilies();
  }


  //chiude l'observable che ha la funzione di mostrare l'errore
  ngOnDestroy(): void {
    this.errorSub.unsubscribe;
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
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

  fetchFamilies(){
    this.errorSub = this.familyService.error.subscribe(
      errorMessage => {
        this.error = errorMessage;
      }
    );
    this.isFetching = true;
    this.familyService.getFamilies().subscribe(
      families => {
        this.isFetching = false;
        this.loadedFamilies = families;
        console.log(this.loadedFamilies);
        console.log('Families');
        this.dataSource.data = this.loadedFamilies;
      },
      error => {
        this.isFetching = false;
        this.error = error.message;
      }
    );
  }

  delFamily(selectedFamilyId: number) {
    console.log(selectedFamilyId);
    this.familyService
      .deleteFamily(selectedFamilyId)
      .subscribe();
    window.location.reload();
  }
}
