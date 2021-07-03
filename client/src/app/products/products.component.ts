import { Component, OnInit} from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit{
  pcComponentDisplay: boolean = false; //Gestisce i filtri per i singoli componenti
  PrebuiltDisplay: boolean = false; //Gestisce i filtri per i preassemblati

  ngOnInit(): void {
  }
}
