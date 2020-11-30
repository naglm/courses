package com.example.courses.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 32, unique = true)
    private String code;

    @Column(length = 255)
    private String name;

    @Column(length = 4096)
    private String description;

    private Integer durationMin;

    @Column(length = 64)
    private String language;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<StudentCourseRegistration> registeredStudents = new ArrayList<>();

}
