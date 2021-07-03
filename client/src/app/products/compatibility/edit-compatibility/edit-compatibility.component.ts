import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Compatibility } from 'src/app/shared/models/compatibility.model';
import { CompatibilityService } from 'src/app/shared/services/compatibility.service';

/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Component({
  selector: 'app-edit-compatibility',
  templateUrl: './edit-compatibility.component.html',
  styleUrls: ['./edit-compatibility.component.scss']
})
export class EditCompatibilityComponent implements OnInit {
  loadedCompatibility: Compatibility;
  isFetching = false;
  error = null;
  private errorSub: Subscription;

  @ViewChild('f', { static: false }) updateForm: NgForm;

  constructor(private compatibilityService: CompatibilityService,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit(): void {
    this.fetchPcComponent();
  }

  onUpdateCompatibility() {
    this.loadedCompatibility.componentFamilyId1 = this.updateForm.value.id1;
    this.loadedCompatibility.componentFamilyId2 = this.updateForm.value.id2;
    this.compatibilityService.updateCompatibility(this.loadedCompatibility, this.loadedCompatibility.id).subscribe();
    this.router.navigate(['/products/pc-components'], {relativeTo: this.route});
  }

  fetchPcComponent(){
    this.errorSub = this.compatibilityService.error.subscribe(
      errorMessage => {
        this.error = errorMessage;
      }
    );
    this.route.fragment.subscribe();
    const id = +this.route.snapshot.params['id'];
    this.isFetching = true;
    this.compatibilityService.getCompatibility(id).subscribe(
      compatibility => {
        this.isFetching = false;
        this.loadedCompatibility = compatibility;
        console.log(this.loadedCompatibility);
      },
      error => {
        this.isFetching = false;
        this.error = error.message;
      }
    );
  }
}
