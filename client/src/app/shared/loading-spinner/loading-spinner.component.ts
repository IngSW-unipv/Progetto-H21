import { Component } from "@angular/core";

/**
 * @author Filippo Casarosa
 */
@Component({
  selector: 'app-loading-spinner',
  template: '<div class="lds-ellipsis"><div></div><div></div><div></div><div></div></div>',
  styleUrls: ['./loading-spinner.component.css']
})
export class LoadingSpinner{

}
