import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Family } from 'src/app/shared/models/family.model';
import { FamilyService } from 'src/app/shared/services/family.service';

/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Component({
  selector: 'app-edit-family',
  templateUrl: './edit-family.component.html',
  styleUrls: ['./edit-family.component.scss']
})
export class EditFamilyComponent implements OnInit {
  loadedFamily = {} as  Family;
  isFetching = false;
  error = null;
  private errorSub: Subscription;

  @ViewChild('f', { static: false }) updateForm: NgForm;

  constructor(private familyService: FamilyService,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit(): void {
    this.fetchPcComponent();
  }

  onUpdateFamily() {
    this.loadedFamily.name = this.updateForm.value.name;
    this.loadedFamily.typeId = this.updateForm.value.typeId;
    this.familyService.updateFamily(this.loadedFamily, this.loadedFamily.id).subscribe();
    this.router.navigate(['/products/family'], {relativeTo: this.route});
  }

  fetchPcComponent(){
    this.errorSub = this.familyService.error.subscribe(
      errorMessage => {
        this.error = errorMessage;
      }
    );
    this.route.fragment.subscribe();
    const id = +this.route.snapshot.params['id'];
    this.isFetching = true;
    this.familyService.getFamily(id).subscribe(
      family => {
        this.isFetching = false;
        this.loadedFamily = family;
        console.log(this.familyService);
      },
      error => {
        this.isFetching = false;
        this.error = error.message;
      }
    );
  }
}
