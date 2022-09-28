package com.example.Student.Controller

import com.example.Student.Model.Student
import com.example.Student.Service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("student")
class StudentController
    (@Autowired
    val studentService : StudentService){

    @GetMapping("lists")
    fun getAllUsers(): Flux<Student> {
        return studentService.findAllStudent()
    }


    @GetMapping("find/{studentId}")
    fun findStudentById(@PathVariable("studentId") studentId: Int): Mono<Student> {
        return studentService.findStudentById(studentId)
    }

    @PostMapping("add")
    fun addStudent(@RequestBody student: Student): Mono<Student> {
        return studentService.addStudent(student)
    }

    @PutMapping("update/{studentId}")
    fun updateStudentById(@PathVariable("studentId") studentId: Int, @RequestBody student: Student): Mono<Student> {
        return studentService.updateStudent(studentId, student)
    }

    @DeleteMapping("delete/{studentId}")
    fun deleteStudentById(@PathVariable("studentId") studentId: Int): Mono<Void> {
        return studentService.deleteStudentById(studentId)
    }
}