package com.example.Student.Controller

import com.example.Student.Model.Student
import com.example.Student.Service.StudentService
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(StudentController::class)
@AutoConfigureWebTestClient
class StudentControllerTest {
    @Autowired
    lateinit var client: WebTestClient

     @Autowired
    lateinit var studentService: StudentService

    @Test

    fun `should return list of students`() {
        val student1 = Student(1, "Saurabh",  "Mechanical")
        val student2 = Student(2, "Nitin",  "Civil")

        val expectedResult = listOf(
            mapOf(
                "studentId" to 1,
                "studentName" to "Saurabh",
                "studentDepartment" to "Mechanical"
            ),
            mapOf(
                "studentId" to 2,
                "studentName" to "Nitin",
                "studentDepartment" to "Civil"
            ),
        )

        every {
            studentService.findAllStudent()
        } returns Flux.just(student1, student2)

        val response = client.get()
            .uri("/student/lists")
            .accept(MediaType.APPLICATION_JSON)
            .exchange() //invoking the end point
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody


        response.blockFirst() shouldBe expectedResult[0]
        //response.blockLast() shouldBe expectedResult[1]

        verify(exactly = 1) {
            studentService.findAllStudent()
        }
    }
    @Test
    fun `should return a single student`() {

        val exepectedResponse = mapOf(
            "studentId" to 2,
            "studentName" to "Sunny",
            "studentDepartment" to "Civil"
        )

        val student1 = Student(2, "Sunny", "Civil")

        every {
            studentService.findStudentById(2)
        } returns Mono.just(student1)


        val response = client.get()
            .uri("/student/find/2")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>().responseBody

        response.blockFirst() shouldBe exepectedResponse

        verify(exactly = 1) {
            studentService.findStudentById(2)
        }
    }

        @Test
        fun `should create student when create api is being called`() {

            val exepectedResponse = mapOf(
                "studentId" to 2,
                "studentName" to "Sunny K",
                "studentDepartment" to "Civil"
            )

            val student1 = Student(2, "Sunny K", "Civil")

            every {
                studentService.addStudent(student1)
            } returns Mono.just(student1)

            val response = client.post()
                .uri("/student/add")
                .bodyValue(student1)
                .exchange()
                .expectStatus().is2xxSuccessful
                .returnResult<Any>().responseBody

            response.blockFirst() shouldBe exepectedResponse

            verify(exactly = 1) {
                studentService.addStudent(student1)
            }

        }
    @Test
    fun `should be able to update the student`() {

        val expectedResult = mapOf(
            "studentId" to 2,
            "studentName" to "Sunny Kolhe",
            "studentDepartment" to "Civil"
        )

        val student1 = Student(2, "Sunny Kolhe", "Civil")

        every {
            studentService.updateStudent(2,student1)
        } returns Mono.just(student1)

        val response = client.put()
            .uri("/student/update/2")
            .bodyValue(student1)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody

        response.blockFirst() shouldBe expectedResult

        verify(exactly = 1) {
            studentService.updateStudent(2,student1)
        }
    }

    @Test
    fun `should be able to delete the student`() {

        val expectedResult = listOf(
            mapOf("studentId" to 1,
                "studentName" to "Saurabh",
                "studentDepartment" to "Mechanical" ))

        val student = Student(1,"Saurabh" , "Mechanical")

        every {
            studentService.deleteStudentById(1)
        }returns  Mono.empty()

        val response = client.delete()
            .uri("/student/delete/1")
            .exchange()
            .expectStatus().is2xxSuccessful

        verify(exactly = 1) {
            studentService.deleteStudentById(1)
        }
    }



    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun studentService() = mockk<StudentService>()
    }
}