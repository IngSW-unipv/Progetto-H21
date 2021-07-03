import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA,  MatDialogRef } from '@angular/material/dialog';
import { Prebuilt } from 'src/app/shared/models/prebuilt.model';
import { PrebuiltsListComponent } from 'src/app/prebuilts/prebuilts-list/prebuilts-list.component';

/**
 * @author Filippo Casarosa
 */
@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss']
})
export class DialogComponent implements OnInit {
  prebuiltSelected = false;

  constructor(
    public dialogRef: MatDialogRef<PrebuiltsListComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Prebuilt) {}


  ngOnInit(): void {
  }

  /**
   * Gestisce il comportamento del dialog box nel caso in cui si scelga "No"
   * @author Filippo Casarosa
   */
  onNoClick(): void {
    this.prebuiltSelected = false; //Scegliendo "No", non viene selezionato nessun preassemblato
    this.dialogRef.close();
  }

  /**
   * Gestisce il comportamento del dialog box nel caso in cui si scelga "OK"
   * @author Filippo Casarosa
   */
  onOkClick(): void {
    this.prebuiltSelected = true;
  }
}
