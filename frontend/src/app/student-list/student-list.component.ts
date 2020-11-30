import { Component, OnInit } from '@angular/core';
import {StudentService} from "../services/student.service";
import {Student} from "../model/student";

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent implements OnInit {

  students: Student[];
  totalElements: number = 0;
  showingFrom: number = 0;
  showingTo: number = 0;

  constructor(private studentService: StudentService) { }

  ngOnInit(): void {
      this.studentService.findAll().subscribe(data => {
          this.students = data['content'];
          this.totalElements = data['totalElements'];
          this.showingFrom = data['pageable']['pageNumber'] * data['pageable']['pageSize'] + 1;
          this.showingTo = data['pageable']['pageNumber'] * data['pageable']['pageSize'] + data['pageable']['pageSize'];
        }
    );
  }

}
