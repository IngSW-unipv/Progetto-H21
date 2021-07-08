import { Component, OnInit, ViewChild } from "@angular/core";
import { NgForm } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";
import { PcComponents } from "src/app/shared/models/pc-components-model";
import { PcComponentsService } from "src/app/shared/services/pc-components.service";

/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Component({
  selector: 'app-edit-pc-component',
  templateUrl: './edit-pc-component.component.html',
  styleUrls: ['./edit-pc-component.component.scss']
})
export class EditPcComponentComponent implements OnInit {
  loadedPcComponent = {} as PcComponents;
  isFetching = false;
  error = null;
  private errorSub: Subscription;
  saveComplete: boolean;

  @ViewChild('f', { static: false }) updateForm: NgForm;

  constructor(private pcComponentService: PcComponentsService,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit(): void {
    this.fetchPcComponent();
    this.saveComplete = false;
  }

  onUpdateComponent() {
    this.loadedPcComponent.name = this.updateForm.value.name;
    this.loadedPcComponent.price = this.updateForm.value.price;
    this.loadedPcComponent.power = this.updateForm.value.power;
    this.loadedPcComponent.familyId = this.updateForm.value.familyId;
    this.pcComponentService.updatePcComponent(this.loadedPcComponent, this.loadedPcComponent.id).subscribe();
    this.saveComplete = true;
    // this.router.navigate(['/products/pc-components'], {relativeTo: this.route});
    // window.location.reload();
  }

  fetchPcComponent(){
    this.errorSub = this.pcComponentService.error.subscribe(
      errorMessage => {
        this.error = errorMessage;
      }
    );
    this.route.fragment.subscribe();
    const id = +this.route.snapshot.params['id'];
    this.isFetching = true;
    this.pcComponentService.getPcComponent(id).subscribe(
      pcComponent => {
        this.isFetching = false;
        this.loadedPcComponent = pcComponent;
        console.log(this.loadedPcComponent);
      },
      error => {
        this.isFetching = false;
        this.error = error.message;
      }
    );
  }
}
