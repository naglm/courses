import { Injectable } from '@angular/core';
import { environment } from './../../environments/environment';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Course} from "../model/course";

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  constructor(private http: HttpClient) { }

  listCourses(request) : Observable<Course[]> {
    const endpoint = environment.apiUrl + "/courses";
    const params = request;
    let test : Observable<Course[]> = this.http.get<Course[]>(endpoint, { params });
    return test;
  }
}
