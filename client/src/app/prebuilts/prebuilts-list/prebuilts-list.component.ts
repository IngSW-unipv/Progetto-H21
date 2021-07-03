import { Component, OnDestroy, OnInit } from '@angular/core';
import { Prebuilt } from '../../shared/models/prebuilt.model';
import { PrebuiltService } from '../../shared/services/prebuilt.service';
import { Subscription } from 'rxjs';
import { DialogComponent } from 'src/app/shared/dialog/dialog.component';
import { MatDialog } from '@angular/material/dialog';

/**
 * @author Filippo Casarosa
 */
@Component({
  selector: 'app-prebuilts-list',
  templateUrl: './prebuilts-list.component.html',
  styleUrls: ['./prebuilts-list.component.scss']
})
export class PrebuiltsListComponent implements OnInit, OnDestroy {
  loadedPrebuilts: Prebuilt[] = [];
  isFetching = false;
  error = null;
  private errorSub: Subscription;

  constructor(private prebuiltService: PrebuiltService,
              private dialog: MatDialog) { }

  /**
   * All'avvio del componente prebuilts, vengono mostrati tutti i computer preassemblati
   * tramite l'API definita del service di prebuilt
   * @author Filippo Casarosa
   */
  ngOnInit() {
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
      },
      error => {
        this.isFetching = false;
        this.error = error.message;
      }
    );
  }

  /**
   * Chiude l'observable che ha la funzione di mostrare l'errore
   * @author Filippo Casarosa
   */
  ngOnDestroy(): void {
    this.errorSub.unsubscribe;
  }

  /**
   * Resetta l'errore a null
   * @author Filippo Casarosa
   */
  onHandleError(){
    this.error = null;
  }

  /**
   * Quando viene selezionato il prebuilt, apre un dialog box per confermare la scelta
   * @author Filippo Casarosa
   */
  onPrebuiltSelected(){
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '330px',
      // data: {name: this.name, animal: this.animal}
    });
    console.log(this.prebuiltService.getPrebuilt(1))
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
