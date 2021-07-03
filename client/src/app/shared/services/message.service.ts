import { Injectable } from '@angular/core';

/**
 * @author Filippo Casarosa
 * @author Andrei Blindu
 */
@Injectable({ providedIn: 'root' })
export class MessageService {
  messages: string[] = [];

  add(message: string) {
    this.messages.push(message);
  }

  clear() {
    this.messages = [];
  }
}
