import { Component, OnInit } from '@angular/core';
import {CourseService} from "../services/course.service";
import {PageEvent} from "@angular/material/paginator";
import {Course} from "../model/course";

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css']
})
export class CourseListComponent implements OnInit {

  totalElements = 0;
  courses : Course[];
  loading: boolean;

  constructor(private courseService : CourseService) { }

  ngOnInit(): void {
    this.getCourses({ page: '0', size: '10' });

  }

  private getCourses(request) {
    this.loading = true;
    this.courseService.listCourses(request)
        .subscribe(data => {
          this.courses = data['content'];
          this.totalElements = data['totalElements'];
          this.loading = false;
        }, error => {
          this.loading = false;
        });
  }

  nextPage(event: PageEvent) {
    const request = {};
    request['page'] = event.pageIndex.toString();
    request['size'] = event.pageSize.toString();
    this.getCourses(request);
  }
}
