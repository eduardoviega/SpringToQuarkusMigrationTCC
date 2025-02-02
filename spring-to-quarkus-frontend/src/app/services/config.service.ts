import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class ConfigService {

    private url: String = "http://localhost:8080";

    constructor(private http: HttpClient) { }

    migrateProject(formData: FormData, promptId: number): Observable<any> {
        return this.http.post(this.url + `/migrate/files/${promptId}`, formData);
    }
}