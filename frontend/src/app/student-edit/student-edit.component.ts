import { Component, OnInit } from '@angular/core';
import {Student} from "../model/student";
import {StudentService} from "../services/student.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-student-edit',
  templateUrl: './student-edit.component.html',
  styleUrls: ['./student-edit.component.css']
})
export class StudentEditComponent implements OnInit {

  student: Student

  constructor(private studentService: StudentService, private router: Router) { }

  ngOnInit(): void {
    this.student = new Student();
  }

  onSubmit() {
    this.studentService.save(this.student).subscribe(result => {
      this.gotoStudentList();
    });
  }

  gotoStudentList() {
    this.router.navigate(['/students']);
  }
}
