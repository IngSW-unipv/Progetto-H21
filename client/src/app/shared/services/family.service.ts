import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of, Subject } from "rxjs";
import { catchError, tap } from "rxjs/operators";
import { MessageService } from "src/app/shared/services/message.service";
import { Family } from "../models/family.model";

/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Injectable({providedIn: 'root'})
export class FamilyService{
  familyChanged = new Subject<Family[]>();
  error = new Subject<string>();
  private familiesUrl: string = 'http://localhost:8080/componentFamilies'
  private familyUrl: string = 'http://localhost:8080/componentFamily'

  constructor(private http: HttpClient,
              private messageService: MessageService) {}

  /** GET prebuilts from the server */
  getFamilies(): Observable<Family[]> {
    return this.http.get<Family[]>(this.familiesUrl)
      .pipe(
        tap(_ => this.log('fetched family')),
        catchError(this.handleError<Family[]>('getFamilies', []))
      );
  }

    /** GET component by id. Will 404 if id not found */
    getFamily(id: number): Observable<Family> {
      const url = `${this.familiesUrl}/${id}`;
      return this.http.get<Family>(url).pipe(
        tap(_ => this.log(`fetched Family id=${id}`)),
        catchError(this.handleError<Family>(`getFamily id=${id}`))
      );
    }

    /** POST: add a new prebuilt to the server  */
    addFamily(family: Family): Observable<Family> {
      return this.http.post<Family>(this.familyUrl, family).pipe(
        tap((newFamily: Family) => this.log(`added family w/ id=${newFamily.id}`)),
        catchError(this.handleError<Family>('addFamily'))
      );
    }

    /** DELETE: delete the prebuilt from the server */
    deleteFamily(id: number): Observable<Family> {
      const url = `${this.familyUrl}/${id}`;

      return this.http.delete<Family>(url).pipe(
        tap(_ => this.log(`deleted family id=${id}`)),
        catchError(this.handleError<Family>('deleteFamily'))
      );
    }

    /** PUT: update the prebuilt on the server */
    updateFamily(family: Family, id: number): Observable<any> {
      const url = `${this.familyUrl}/${id}`;
      return this.http.put(url, family).pipe(
        tap(_ => this.log(`updated family id=${family.id}`)),
        catchError(this.handleError<any>('updateFamily'))
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
    this.messageService.add(`PrebuiltService: ${message}`);
  }
}
