package com.example.courses;

import com.example.courses.entities.*;
import com.example.courses.repository.jpa.CourseRepository;
import com.example.courses.repository.jpa.EmployeeRepository;
import com.example.courses.repository.jpa.StudentCourseRegistrationRepository;
import com.example.courses.repository.jpa.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class CoursesApplication {

    public static final String PROFILE_DEV = "dev";
    public static final String PROFILE_TEST = "test";

    Logger logger = LoggerFactory.getLogger(CoursesApplication.class);

    @Value("classpath:data/tutorials.txt")
    private Resource tutorialData;

    public static void main(String[] args) {
        SpringApplication.run(CoursesApplication.class, args);
    }

    @Bean
    public CommandLineRunner initEmployees(EmployeeRepository employeeRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                employeeRepository.save(new Employee("Johnny Worker", "worker"));
                employeeRepository.save(new Employee("Johnny2 Worker2", "worker"));
                employeeRepository.save(new Employee("Johnny3 Worker3", "worker"));
                employeeRepository.save(new Employee("Hugo Boss", "manager"));
                employeeRepository.save(new Employee("Donald Duck", "owner"));
                employeeRepository.save(new Employee("Freddy Mercury", "admin"));
            }
        };
    }

    @Bean
    public CommandLineRunner initTutorials() {
        return (args) -> {
            logger.debug("tutorial data: " + tutorialData.getFilename());
        };
    }

    @Bean
    @Profile("!test")
    public CommandLineRunner initStudents(StudentRepository studentRepository, CourseRepository courseRepository, StudentCourseRegistrationRepository studentCourseRegRepo) {
        return (args) -> {
            Course c1 = new Course(
                    null,
                    "math-101",
                    "Mathematics 101 - Basic course",
                    "Math 101 is a first-level course in the fulfillment of the mathematics requirement for graduation at the University of Kansas",
                    120,
                    "English",
                    new ArrayList<>());
            courseRepository.save(c1);
            Student s1 = new Student(null, "Martin", "Student", LocalDate.of(1983, 1,1), "some@email.com", new ArrayList<>());
            studentRepository.save(s1);

            StudentCourseRegistration scr1 = new StudentCourseRegistration(null, LocalDate.now(), s1, c1);
            s1.getCourseRegistrations().add(scr1);
            studentCourseRegRepo.save(scr1);

            Course c2 = new Course(null,
                    "spring-basic",
                    "Spring Framework - Basic course",
                    "Introduction to Spring Framework for beginners. This Spring tutorial is designed for Java programmers who need to understand the Spring framework and its application. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eget maximus sem. Quisque et magna suscipit, hendrerit quam id, aliquam urna. Etiam varius pharetra erat, non posuere enim. Phasellus rhoncus massa varius libero accumsan, nec elementum magna porta. Aenean pharetra molestie velit, ut facilisis neque eleifend nec. Suspendisse tempus, lorem at tincidunt hendrerit, sapien sapien malesuada quam, in pharetra massa leo quis neque. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vestibulum felis lectus, dignissim in urna vitae, dapibus rutrum odio. Morbi tellus quam, condimentum iaculis congue a, suscipit sed dolor. Nulla ornare dui quis tortor pharetra pharetra.",
                    120,
                    "English",
                    new ArrayList<>());
            courseRepository.save(c2);

            Course c3 = new Course(null,
                    "spring-advanced",
                    "Spring Framework - Advanced",
                    "Advanced Spring Framework course. Prerequisite: Introduction to Spring framework for beginners. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eget maximus sem. Quisque et magna suscipit, hendrerit quam id, aliquam urna. Etiam varius pharetra erat, non posuere enim. Phasellus rhoncus massa varius libero accumsan, nec elementum magna porta. Aenean pharetra molestie velit, ut facilisis neque eleifend nec. Suspendisse tempus, lorem at tincidunt hendrerit, sapien sapien malesuada quam, in pharetra massa leo quis neque. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vestibulum felis lectus, dignissim in urna vitae, dapibus rutrum odio. Morbi tellus quam, condimentum iaculis congue a, suscipit sed dolor. Nulla ornare dui quis tortor pharetra pharetra.",
                    180,
                    "English",
                    new ArrayList<>());
            courseRepository.save(c3);

            Course c4 = new Course(null,
                    "angular-basic",
                    "Angular - Basic course",
                    "Introduction to Angular for beginners. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eget maximus sem. Quisque et magna suscipit, hendrerit quam id, aliquam urna. Etiam varius pharetra erat, non posuere enim. Phasellus rhoncus massa varius libero accumsan, nec elementum magna porta. Aenean pharetra molestie velit, ut facilisis neque eleifend nec. Suspendisse tempus, lorem at tincidunt hendrerit, sapien sapien malesuada quam, in pharetra massa leo quis neque. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vestibulum felis lectus, dignissim in urna vitae, dapibus rutrum odio. Morbi tellus quam, condimentum iaculis congue a, suscipit sed dolor. Nulla ornare dui quis tortor pharetra pharetra.",
                    150,
                    "English",
                    new ArrayList<>());
            courseRepository.save(c4);


            studentRepository.save(new Student(null, "Martin", "Student", LocalDate.of(1983, 1,1), "some@email.com", new ArrayList<>()));
            studentRepository.save(new Student(null, "John", "HardLearner", LocalDate.of(1980, 2,2), "john@example.com", new ArrayList<>()));
            studentRepository.save(new Student(null, "Freddy", "Example", LocalDate.of(1990, 1,1), "no@example.com", new ArrayList<>()));
            studentRepository.save(new Student(null, "Freddy2", "Example", LocalDate.of(1990, 1,1), "no@example.com", new ArrayList<>()));
            studentRepository.save(new Student(null, "Freddy3", "Example", LocalDate.of(1990, 1,1), "no@example.com", new ArrayList<>()));
            studentRepository.save(new Student(null, "Freddy4", "Example", LocalDate.of(1990, 1,1), "no@example.com", new ArrayList<>()));
            studentRepository.save(new Student(null, "Freddy5", "Example", LocalDate.of(1990, 1,1), "no@example.com", new ArrayList<>()));


        };
    }
}
