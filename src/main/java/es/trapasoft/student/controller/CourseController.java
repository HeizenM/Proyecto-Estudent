package es.trapasoft.student.controller;

import es.trapasoft.student.entity.Course;
import es.trapasoft.student.repository.IStudentRepository;
import es.trapasoft.student.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private IStudentRepository studentRepository;

    public CourseController() {
    }

    @GetMapping("/all")
    public String courses(Model model) {
        model.addAttribute("courses",
                courseService.getAllCourses());
        return "courses";
    }

    @GetMapping("/create")
    public String createCourseForm(Model model) {
//  este objeto Course almacenara los valores
        Course course = new Course();
        model.addAttribute("course", course);
        return "create_course";
    }

    @PostMapping("/save")
    public String saveCourse(@ModelAttribute("course") Course course) {
        courseService.saveCourse(course);
        return "redirect:/courses/all";
    }

    @GetMapping("/edit/{id}")
    public String editCourseForm(@PathVariable Long id, Model model) {
        Course cour = courseService.getCourseById(id);
        model.addAttribute("one_course", cour);
        return "edit_course";
    }

    @PostMapping("/save/{id}")
    public String saveCourse(@PathVariable Long id, @ModelAttribute("course") Course course, Model model) {

        //sacar el curso de la b.d. por el id
        Course existentCourse = courseService.getCourseById(id);

        // cargarlo
        existentCourse.setId(id);
        existentCourse.setName(course.getName());
        existentCourse.setCredits(course.getCredits());
        existentCourse.setFee(course.getFee());
        existentCourse.setModules(course.getModules());
        courseService.updateCourse(existentCourse);

        return "redirect:/courses/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourseById(id);
        return "redirect:/courses/all";
    }

}
