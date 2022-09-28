package com.example.Student.Service

import com.example.Student.Model.Student
import com.example.Student.Repository.StudentRepository
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class StudentServiceTest {

     // mocking the repository layer response
     val student1 = Student(1, "Saurabh",  "Mechanical")
     val student2 = Student(888, "Sunny",  "Civil")

     private val studentRepository = mockk<StudentRepository>() {

         every {
             findAll()
         } returns Flux.just(student1, student2)

         every {
             findById(1)
         }returns Mono.just(student1)


     }

     private val studentService = StudentService(studentRepository)

    @Test
    fun `should return users when findAllStudent  method is called`() {

        val firstUser = studentService.findAllStudent().blockFirst()
        val secondUser = studentService.findAllStudent().blockLast()

        if (firstUser != null) {
            firstUser shouldBe student1
        }
        if (secondUser != null) {
            secondUser shouldBe student2
        }
    }

    @Test
    fun `Test Find User By Id`() {

        val result=studentService.findStudentById(1).block()

        result shouldBe student1

    }

    @Test
    fun `Test adding Student`() {
        val student1 = Student(1, "Saurabh",  "Mechanical")

        every{
            studentRepository.save(student1)
        }returns Mono.just(student1)

        val addedUser = studentService.addStudent(student1).block()

        addedUser shouldBe student1
    }

    @Test
    fun `Test update Student`() {

        // val user1 = User(999,"Rahul K",9999999999,"Aaaaa@aaa")
        every{
            studentRepository.save(student1)
        }returns Mono.just(student1)
        val updatedUser = studentService.updateStudent(1,student1).block()

        updatedUser shouldBe student1
    }

    @Test
    fun `delete student By Id`() {

        every{
            studentRepository.deleteById(1)
        }returns Mono.empty()
    }




}