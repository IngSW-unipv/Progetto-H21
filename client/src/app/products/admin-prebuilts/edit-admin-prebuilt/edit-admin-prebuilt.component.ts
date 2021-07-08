import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ListService } from 'src/app/shared/services/list.service';
import { Prebuilt } from 'src/app/shared/models/prebuilt.model';
import { PrebuiltService } from 'src/app/shared/services/prebuilt.service';
/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Component({
  selector: 'app-edit-admin-prebuilt',
  templateUrl: './edit-admin-prebuilt.component.html',
  styleUrls: ['./edit-admin-prebuilt.component.scss']
})
export class EditAdminPrebuiltComponent implements OnInit {
  loadedPrebuilt = {} as Prebuilt;
  isFetching = false;
  error = null;
  private errorSub: Subscription;
  saveComplete: boolean;

  @ViewChild('f', { static: false }) updateForm: NgForm;


  constructor(private prebuiltService: PrebuiltService,
              private listService: ListService,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit(): void {
    this.fetchPrebuilt();
    this.saveComplete = false;
  }

  onUpdatePrebuilt() {
    this.loadedPrebuilt.name = this.updateForm.value.name;
    this.loadedPrebuilt.totalPrice = this.updateForm.value.totalPrice;
    this.loadedPrebuilt.usage = this.updateForm.value.usage;
    this.loadedPrebuilt.componentList =  this.listService.getList().map((pcComponent) => {
      return pcComponent.id
    })
    this.prebuiltService.updatePrebuilt(this.loadedPrebuilt, this.loadedPrebuilt.id).subscribe();
    this.saveComplete= true;
    // this.router.navigate(['/products/admin-prebuilts'], {relativeTo: this.route});
  }

  fetchPrebuilt(){
    this.errorSub = this.prebuiltService.error.subscribe(
      errorMessage => {
        this.error = errorMessage;
      }
    );
    this.route.fragment.subscribe();
    const id = +this.route.snapshot.params['id'];
    this.isFetching = true;
    this.prebuiltService.getPrebuilt(id).subscribe(
      prebuilt => {
        this.isFetching = false;
        this.loadedPrebuilt = prebuilt;
        console.log(this.loadedPrebuilt);
      },
      error => {
        this.isFetching = false;
        this.error = error.message;
      }
    );
  }
}
