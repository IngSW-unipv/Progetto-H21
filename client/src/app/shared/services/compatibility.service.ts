import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of, Subject } from "rxjs";
import { catchError, tap } from "rxjs/operators";
import { Compatibility } from "../models/compatibility.model";
import { PcComponents } from "../models/pc-components-model";
import { MessageService } from "./message.service";

/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Injectable({providedIn: 'root'})
export class CompatibilityService{
  CompatibilyChanged = new Subject<Compatibility[]>();
  pcComponentsChanged = new Subject<PcComponents[]>();
  error = new Subject<string>();

  private CompatibilityUrl: string = 'http://localhost:8080/compatibilityConstraints'
  private CompatibilitUrl: string = 'http://localhost:8080/compatibilityConstraint'

  constructor(private http: HttpClient,
              private messageService: MessageService) {}

  /** GET all the components that are compatible with a given component*/
  getComponentsByCompatibility(id: number): Observable<PcComponents[]> {
    const url = `http://localhost:8080/compatibilityConstraints/getByComponentId/${id}`
    return this.http.get<PcComponents[]>(url).pipe(
        tap(_ => this.log('fetched Components by Compatibility')),
        catchError(this.handleError<PcComponents[]>('getComponentsByCompatibility', []))
      );
  }

  /** GET prebuilts from the server */
  getCompatibilies(): Observable<Compatibility[]> {
    return this.http.get<Compatibility[]>(this.CompatibilityUrl).pipe(
        tap(_ => this.log('fetched Compatibilities')),
        catchError(this.handleError<Compatibility[]>('getCompatibilies', []))
      );
  }

  /** GET Compatibility by id. Will 404 if id not found */
  getCompatibility(id: number): Observable<Compatibility> {
    const url = `${this.CompatibilityUrl}/${id}`;
    return this.http.get<Compatibility>(url).pipe(
      tap(_ => this.log(`fetched Compatibility id=${id}`)),
      catchError(this.handleError<Compatibility>(`getCompatibility id=${id}`))
    );
  }

    /** POST: add a new prebuilt to the server  */
    addCompatibility(family: Compatibility): Observable<Compatibility> {
      return this.http.post<Compatibility>(this.CompatibilitUrl, family).pipe(
        tap((newCompatibility: Compatibility) => this.log(`added Compatibility w/ id=${newCompatibility.id}`)),
        catchError(this.handleError<Compatibility>('addCompatibility'))
      );
    }

    /** DELETE: delete the prebuilt from the server */
    deleteCompatibility(id: number): Observable<Compatibility> {
      const url = `${this.CompatibilitUrl}/${id}`;

      return this.http.delete<Compatibility>(url).pipe(
        tap(_ => this.log(`deleted Compatibility id=${id}`)),
        catchError(this.handleError<Compatibility>('deleteCompatibility'))
      );
    }

    /** PUT: update the prebuilt on the server */
    updateCompatibility(compatibility: Compatibility, id: number): Observable<any> {
      const url = `${this.CompatibilitUrl}/${id}`;
      return this.http.put(this.CompatibilitUrl, compatibility).pipe(
        tap(_ => this.log(`updated Compatibility id=${compatibility.id}`)),
        catchError(this.handleError<any>('updateCompatibility'))
      );
    }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a PrebuiltService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`CompatibilityService: ${message}`);
  }
}
