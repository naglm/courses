import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { environment } from './../../environments/environment';
import {Student} from "../model/student";
import {catchError, tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private studentsUrl: string;

  constructor(private http : HttpClient) {
    this.studentsUrl = environment.apiUrl + "/students";

  }

  public findAll(): Observable<Student[]> {
    return this.http.get<Student[]>(this.studentsUrl);
  }

  public save(student: Student) {
    return this.http.post<Student>(this.studentsUrl, student);
  }

  public deleteStudent(id: string) {
    const url = this.studentsUrl + "/" + id;
    return this.http.delete<Student>(url);
  }
}
