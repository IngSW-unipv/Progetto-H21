import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Family } from 'src/app/shared/models/family.model';
import { PcComponents } from 'src/app/shared/models/pc-components-model';
import { PcComponentsService } from 'src/app/shared/services/pc-components.service';

/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Component({
  selector: 'app-add-pc-component',
  templateUrl: './add-pc-component.component.html',
  styleUrls: ['./add-pc-component.component.scss']
})
export class AddPcComponentComponent implements OnInit {
  newPcComponent: PcComponents = new PcComponents;

  @ViewChild('f', { static: false }) updateForm: NgForm;

  constructor(private pcComponentService: PcComponentsService,
              private router: Router) {}

  ngOnInit(): void {

  }

  onAddComponent() {
    this.newPcComponent.name = this.updateForm.value.name;
    this.newPcComponent.price = this.updateForm.value.price;
    this.newPcComponent.power = this.updateForm.value.power;
    this.newPcComponent.familyId = this.updateForm.value.familyId;
    this.pcComponentService.addPcComponent(this.newPcComponent).subscribe();
    this.router.navigate(['/products/pc-components']);
  }
}
