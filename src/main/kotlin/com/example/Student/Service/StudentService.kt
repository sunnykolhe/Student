package com.example.Student.Service

import com.example.Student.Model.Student
import com.example.Student.Repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class StudentService (
    @Autowired
    val studentRepository: StudentRepository
        ){
    fun findAllStudent(): Flux<Student> {
        return studentRepository.findAll()

    }

    fun findStudentById(studentId: Int): Mono<Student> {
        return studentRepository.findById(studentId)

    }

    fun addStudent(student: Student): Mono<Student> {
        return studentRepository.save(student)

    }

    fun updateStudent(studentId: Int, student: Student): Mono<Student> {
        return studentRepository.findById(studentId)
            .flatMap {
                it.studentId = student.studentId
                it.studentName = student.studentName
                it.studentDepartment = student.studentDepartment
                studentRepository.save(it)
            }

    }

    fun deleteStudentById(studentId: Int): Mono<Void> {
        return studentRepository.deleteById(studentId)

    }
}