package com.example.Student.Repository

import com.example.Student.Model.Student
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface StudentRepository : ReactiveCrudRepository<Student, Int>{

}
