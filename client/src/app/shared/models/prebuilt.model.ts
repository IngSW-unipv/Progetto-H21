/**
 * Modello per la gestione dei preassemblati
 * @author Filippo Casarosa
 */

import { Image } from "./image.model";
import { PcComponents } from "./pc-components-model";

export class Prebuilt{
  public id: number; //Identificativo del PC
  public name: string; //Nome commerciale del PC
  public use: string; //Utilizzo finale (casa, gaming, lavoro)
  public price: number; //Prezzo in euro
  public pcComponents: PcComponents[]; //Componenti installati
  public pcComponentsId: number[] = [] ;
  public imagePathUrl: string; //Immagine del prodotto

}
