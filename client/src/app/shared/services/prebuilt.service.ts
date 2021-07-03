import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of, Subject, throwError } from "rxjs";
import { map, catchError, tap} from 'rxjs/operators';
import { Prebuilt } from "../models/prebuilt.model";
import { MessageService } from "./message.service";

/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Injectable({providedIn: 'root'})
export class PrebuiltService{
  prebuiltsChanged = new Subject<Prebuilt[]>();
  error = new Subject<string>();
  private prebuiltsUrl: string = 'http://localhost:8080/readySetups'
  private prebuiltUrl: string = 'http://localhost:8080/readySetup'

  constructor(private http: HttpClient,
              private messageService: MessageService) {}

  /** GET prebuilts from the server */
  getPrebuilts(): Observable<Prebuilt[]> {
    return this.http.get<Prebuilt[]>(this.prebuiltsUrl)
      .pipe(
        tap(_ => this.log('fetched prebuilts')),
        catchError(this.handleError<Prebuilt[]>('getPrebuilts', []))
      );
  }

  /** GET prebuilt by id. Will 404 if id not found */
  getPrebuilt(id: number): Observable<Prebuilt> {
    const url = `${this.prebuiltsUrl}/${id}`;
    return this.http.get<Prebuilt>(url).pipe(
      tap(_ => this.log(`fetched prebuilt id=${id}`)),
      catchError(this.handleError<Prebuilt>(`getPrebuilt id=${id}`))
    );
  }

  /** POST: add a new prebuilt to the server  */
  addPrebuilt(prebuilt: Prebuilt): Observable<Prebuilt> {
    return this.http.post<Prebuilt>(this.prebuiltUrl, prebuilt).pipe(
      tap((newPrebuilt: Prebuilt) => this.log(`added prebuilt w/ id=${newPrebuilt.id}`)),
      catchError(this.handleError<Prebuilt>('addPrebuilt'))
    );
  }

  /** DELETE: delete the prebuilt from the server */
  deletePrebuilt(id: number): Observable<Prebuilt> {
    const url = `${this.prebuiltUrl}/${id}`;

    return this.http.delete<Prebuilt>(url).pipe(
      tap(_ => this.log(`deleted prebuilt id=${id}`)),
      catchError(this.handleError<Prebuilt>('deletePrebuilt'))
    );
  }

  /** PUT: update the prebuilt on the server */
  updatePrebuilt(prebuilt: Prebuilt, id: number): Observable<any> {
    const url = `${this.prebuiltUrl}/${id}`;
    return this.http.put(url, prebuilt).pipe(
      tap(_ => this.log(`updated prebuilt id=${prebuilt.id}`)),
      catchError(this.handleError<any>('updatePrebuilt'))
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
      this.messageService.add(`PrebuiltService: ${message}`);
    }
}
