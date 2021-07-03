import { Component, OnInit } from '@angular/core';
import { PrebuiltService } from '../shared/services/prebuilt.service';
/**
 * @author Filippo Casarosa
 */
@Component({
  selector: 'app-prebuilts',
  templateUrl: './prebuilts.component.html',
  styleUrls: ['./prebuilts.component.scss'],
  providers: [PrebuiltService]
})
export class PrebuiltsComponent implements OnInit {

  constructor(private prebuiltService: PrebuiltService) { }

  ngOnInit() {

  }

}
