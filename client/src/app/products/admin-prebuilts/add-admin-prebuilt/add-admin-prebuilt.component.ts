import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Image } from 'src/app/shared/models/image.model';
import { Prebuilt } from 'src/app/shared/models/prebuilt.model';
import { ImageService } from 'src/app/shared/services/image.service';
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
export class AddAdminPrebuiltComponent implements OnInit{
  newPrebuilt: Prebuilt;
  selectedFile: any;//File?
  error = null;
  isFetching = false;
  saveComplete: boolean;

  @ViewChild('f', { static: false }) updateForm: NgForm;

  constructor(private prebuiltService: PrebuiltService,
              private router: Router,
              private imageService: ImageService) {
    this.newPrebuilt = new Prebuilt;
  }

  ngOnInit(): void {
    this.saveComplete = false;
  }

  upload(files){
    this.selectedFile = files.item(0);
  }


  onAddPrebuilt() {
    this.newPrebuilt.name = this.updateForm.value.name;
    this.newPrebuilt.usage = this.updateForm.value.use;
    this.newPrebuilt.totalPrice = this.updateForm.value.price;

    this.prebuiltService.save(this.newPrebuilt, this.selectedFile).subscribe((prebuiltSaved) => {
    this.saveComplete = true;
      //TODO mostrare risultato
    });
  }


}
