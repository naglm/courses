import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { environment } from './../../environments/environment';
import {Student} from "../model/student";

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
}
