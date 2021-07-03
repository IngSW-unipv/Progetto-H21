import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Compatibility } from 'src/app/shared/models/compatibility.model';
import { CompatibilityService } from 'src/app/shared/services/compatibility.service';

/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Component({
  selector: 'app-add-compatibility',
  templateUrl: './add-compatibility.component.html',
  styleUrls: ['./add-compatibility.component.scss']
})
export class AddCompatibilityComponent implements OnInit {
  newCompatibility: Compatibility = new Compatibility;

  @ViewChild('f', { static: false }) updateForm: NgForm;

  constructor(private compatibilityService: CompatibilityService,
              private router: Router) {}

  ngOnInit(): void {

  }

  onAddCompatibility() {
    this.newCompatibility.componentFamilyId1 = this.updateForm.value.id1;
    this.newCompatibility.componentFamilyId2 = this.updateForm.value.id2;
    this.compatibilityService.addCompatibility(this.newCompatibility).subscribe();
    this.router.navigate(['/products/compatibility']);
  }
}

