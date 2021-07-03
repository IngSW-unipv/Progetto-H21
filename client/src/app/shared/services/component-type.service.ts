import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of, Subject, throwError } from "rxjs";
import { catchError, map, tap } from "rxjs/operators";
import { ComponentType } from "../models/component-type.model";
import { MessageService } from "./message.service";

/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Injectable({providedIn:'root'})
export class ComponentTypeService {
  componentTypesChanged = new Subject<ComponentType[]>();
  error = new Subject<string>();
  private componentTypesUrl: string = 'http://localhost:8080/componentTypes';

  constructor(private http: HttpClient,
              private messageService: MessageService) {}

  /** GET prebuilts from the server */
  getComponentType(): Observable<ComponentType[]> {
    return this.http.get<ComponentType[]>(this.componentTypesUrl)
      .pipe(
        tap(_ => this.log('fetched componentType')),
        catchError(this.handleError<ComponentType[]>('getComponentType', []))
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
