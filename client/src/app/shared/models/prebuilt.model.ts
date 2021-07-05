/**
 * Modello per la gestione dei preassemblati
 * @author Filippo Casarosa
 */

import { Image } from "./image.model";
import { PcComponents } from "./pc-components-model";

export class Prebuilt{
  public id: number; //Identificativo del PC
  public name: string;
  public usage: string; //Utilizzo finale (casa, gaming, lavoro)
  public totalPrice: number; //Prezzo in euro
  public componentList: number[]; //Componenti installati
  public imagePath: string; //Immagine del prodotto

}
