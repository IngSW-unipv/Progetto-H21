import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Compatibility } from 'src/app/shared/models/compatibility.model';
import { CompatibilityService } from 'src/app/shared/services/compatibility.service';

/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Component({
  selector: 'app-compatibility-list',
  templateUrl: './compatibility-list.component.html',
  styleUrls: ['./compatibility-list.component.scss']
})
export class CompatibilityListComponent implements OnInit, AfterViewInit {
  loadedCompatibilies: Compatibility[] = [];
  isFetching = false;
  error = null;
  private errorSub: Subscription;
  dataSource: MatTableDataSource<Compatibility>;
  displayedColumns: string[] = ['family1', 'family2', 'actions'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private compatibilityService: CompatibilityService,
              private router: Router) { }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource();
    this.fetchCompatibilities();
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

  fetchCompatibilities(){
    this.errorSub = this.compatibilityService.error.subscribe(
      errorMessage => {
        this.error = errorMessage;
      }
    );
    this.isFetching = true;
    this.compatibilityService.getCompatibilies().subscribe(
      families => {
        this.isFetching = false;
        this.loadedCompatibilies = families;
        console.log(this.loadedCompatibilies);
        console.log('Families');
        this.dataSource.data = this.loadedCompatibilies;
      },
      error => {
        this.isFetching = false;
        this.error = error.message;
      }
    );
  }

  delCompatibility(selectedCompatibilityId: number) {
    console.log(selectedCompatibilityId);
    this.compatibilityService
      .deleteCompatibility(selectedCompatibilityId)
      .subscribe();
    window.location.reload();
  }
}
