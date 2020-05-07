package com.example.demo.controllers;

import com.example.demo.models.Student;
import com.example.demo.repositories.IStudentRepository;
import com.example.demo.repositories.StudentRepositoryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class StudentController {
    ArrayList<Student> fakeDatabase = new ArrayList<>();

    private IStudentRepository studentRepository;

    public StudentController() {
        studentRepository = new StudentRepositoryImpl();
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("students" , studentRepository.readAll());
        return "index";
    }

    @GetMapping("/student")
    @ResponseBody
    public String getStudentByParameter(@RequestParam int id) {
        Student stu = studentRepository.read(id);
        return "The name is: " + stu.getFirstName() + " and the cpr is " + stu.getCpr();
    }

    @GetMapping("/createstudent")
    public String createStudent(Model viewModel){
        //viewModel.addAttribute("students", studentRepository); brug dette til at vise i html
        return"/student/createStudent";
    }

    @PostMapping("/student/addStudent")
    public String addStudent(@ModelAttribute Student studentFromPost){ //studentfrompost fyldes med data fra vores html form
        studentRepository.create(studentFromPost); //vores database objekt kÃ¸rer vores create funktion, der fylder en sql query med studentfrompost data
        return "student/createStudent";
    }


    @GetMapping("/deletestudent")
    @ResponseBody
    public String removeStudentByParameter(@RequestParam int id, Model model) {
        Student stu = studentRepository.read(id);
        model.addAttribute("students", studentRepository.readAll());
        studentRepository.delete(id);
        return "index";
    }
    //detals. select * where id = ??

    @GetMapping("/updatestudent")
    public String updateStudent(){
        return"/student/updateStudent";
    }

    @PostMapping("/student/updatedStudent")
    public String updatedStudent(@ModelAttribute Student studentFromPost){
        studentRepository.update(studentFromPost);
        System.out.println(studentFromPost);
        return "redirect:/../templates/studentList";
    }

}