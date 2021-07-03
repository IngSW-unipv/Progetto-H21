import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Family } from 'src/app/shared/models/family.model';
import { FamilyService } from 'src/app/shared/services/family.service';

/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Component({
  selector: 'app-add-family',
  templateUrl: './add-family.component.html',
  styleUrls: ['./add-family.component.scss']
})
export class AddFamilyComponent implements OnInit {
  newFamily: Family = new Family;

  @ViewChild('f', { static: false }) updateForm: NgForm;

  constructor(private familyService: FamilyService,
              private router: Router) {}

  ngOnInit(): void {

  }

  onAddFamily() {
    this.newFamily.name = this.updateForm.value.name;
    this.newFamily.typeId = this.updateForm.value.typeId;
    this.familyService.addFamily(this.newFamily).subscribe();
    this.router.navigate(['/products/family']);
  }
}

