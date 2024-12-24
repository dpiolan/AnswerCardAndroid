package com.myapp.answercard.data

data class StudentAnswers(
    var studentID:String,
    var answers:List<Char>
)

data class StudentAnswersWithSource(
    var studentID: String,
    var answers: List<Char>,
    var source: Int
)