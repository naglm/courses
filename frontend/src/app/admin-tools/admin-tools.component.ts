import { Component, OnInit } from '@angular/core';
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-admin-tools',
  templateUrl: './admin-tools.component.html',
  styleUrls: ['./admin-tools.component.css']
})
export class AdminToolsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  getApiDocsUrl() {
    return environment.apiDocsUrl;
  }

  getH2consoleUrl() {
    return "http://localhost:8080/h2-console/login.jsp";
  }
}
