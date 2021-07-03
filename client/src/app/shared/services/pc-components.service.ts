import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of, Subject, throwError } from "rxjs";
import { catchError, map, tap } from "rxjs/operators";
import { PcComponents } from "../models/pc-components-model";
import { MessageService } from "./message.service";

/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Injectable({providedIn:'root'})
export class PcComponentsService {
  pcComponentsChanged = new Subject<PcComponents[]>();
  error = new Subject<string>();
  private pcComponentsUrl: string = 'http://localhost:8080/components'
  private pcComponentUrl: string = 'http://localhost:8080/component'

  constructor(private http: HttpClient,
              private messageService: MessageService) {}

  /** GET prebuilts from the server */
  getPcComponents(): Observable<PcComponents[]> {
    return this.http.get<PcComponents[]>(this.pcComponentsUrl)
      .pipe(
        tap(_ => this.log('fetched pcComponents')),
        catchError(this.handleError<PcComponents[]>('getPcComponents', []))
      );
  }

  /** GET component by id. Will 404 if id not found */
  getPcComponent(id: number): Observable<PcComponents> {
    const url = `${this.pcComponentsUrl}/${id}`;
    return this.http.get<PcComponents>(url).pipe(
      tap(_ => this.log(`fetched PcComponent id=${id}`)),
      catchError(this.handleError<PcComponents>(`getPcComponent id=${id}`))
    );
  }

  /** GET components by type. Will 404 if id not found */
  getPcComponentsByType(componentTypeId: number): Observable<PcComponents[]> {
    const url = `${this.pcComponentUrl}/${componentTypeId}`;
    return this.http.get<PcComponents[]>(url
    ).pipe(
      tap(_ => this.log(`fetched PcComponents by type`)),
      catchError(this.handleError<PcComponents[]>(`getPcComponentsByType}`))
    );
  }

  /** POST: add a new component to the server  */
  addPcComponent(pcComponent: PcComponents): Observable<PcComponents> {
    return this.http.post<PcComponents>(this.pcComponentUrl, pcComponent).pipe(
      tap((newPcComponent: PcComponents) => this.log(`added PcComponent w/ id=${newPcComponent.id}`)),
      catchError(this.handleError<PcComponents>('addPcComponent'))
    );
  }

  /** DELETE: delete the component from the server */
  deletePcComponent(id: number): Observable<PcComponents> {
    const url = `${this.pcComponentUrl}/${id}`;
    return this.http.delete<PcComponents>(url).pipe(
      tap(_ => this.log(`deleted pcComponent id=${id}`)),
      catchError(this.handleError<PcComponents>('deletePcComponent'))
    );
  }

  /** PUT: update the component on the server */
  updatePcComponent(pcComponent: PcComponents, id: number): Observable<any> {
    const url = `${this.pcComponentUrl}/${id}`;
    return this.http.put(url, pcComponent).pipe(
      tap(_ => this.log(`updated pcComponent id=${pcComponent.id}`)),
      catchError(this.handleError<any>('updatePcComponent'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
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
      this.messageService.add(`PcComponentsService: ${message}`);
    }

}
