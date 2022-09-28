package com.example.Student.Model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Student(
        @Id
        var studentId: Int?,
        var studentName: String,
        var studentDepartment: String

        )
