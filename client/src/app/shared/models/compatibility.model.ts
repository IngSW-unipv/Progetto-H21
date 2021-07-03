/**
 * Modello per la gestione delle compatibilità tra famiglie di prodotti
 * @author Filippo Casarosa
 */

import { Family } from "./family.model";

export class Compatibility {
  public id: number; //ID della compatibilità
  public componentFamily1: Family; //Prima famiglia
  public componentFamily2: Family; //Seconda famiglia
  public componentFamilyId1: number; //Prima famiglia
  public componentFamilyId2: number; //Seconda famiglia
}
