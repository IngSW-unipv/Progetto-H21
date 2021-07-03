/**
 * @author Filippo Casarosa
 */
export class Admin {
  constructor(
    public id: number,
    public name: string,
    private _token: string,
    private _tokenExpireDate: Date
  ) {}

  get token() {
    if(!this._tokenExpireDate || new Date() > this._tokenExpireDate){
      return null;
    }
    return this._token
  }
}
