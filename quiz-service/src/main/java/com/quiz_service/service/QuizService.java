package com.quiz_service.service;

import com.quiz_service.feign.QuizInterface;
import com.quiz_service.model.Answer;
import com.quiz_service.model.QuestionWrapper;
import com.quiz_service.model.Quiz;
//import com.quiz_service.repository.QuestionRepository;
import com.quiz_service.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizInterface quizInterface;

    public void createQuiz(String category, int numQ, String title) {
        List<Integer> questions = quizInterface.HttpGetQuestionIdsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizRepository.save(quiz);
    }

    public List<QuestionWrapper> getQuizQuestions(int id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        List<Integer> questionIds = quiz.get().getQuestionIds();
        List<QuestionWrapper> questions = quizInterface.HttpGetQuestionsFromId(questionIds).getBody();
        return questions;
    }

    public int calculateResult(int id, List<Answer> answers) {
        System.out.println(answers);
        Integer scores = quizInterface.HttpGetScore(answers).getBody();
        System.out.println(scores);
        return scores;
    }
}
