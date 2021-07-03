/**
 * Modello per la gestione dei singoli componenti
 * @author Filippo Casarosa
 */

import { Family } from "./family.model";

export class PcComponents{
  id: number; //or integer still to decide for now we keep string because it's easier to use for now
  name: string; //Nome commerciale del componente
  price: number; //Prezzo in euro
  power?: number; //Potenza consumata dal prodotto in Watt
  componentFamily: Family; //Famiglia di appartenenza del componente
  familyId: number;
}
