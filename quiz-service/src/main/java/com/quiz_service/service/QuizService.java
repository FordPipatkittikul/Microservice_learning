package com.example.demo.service;

import com.example.demo.model.Answer;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionWrapper;
import com.example.demo.model.Quiz;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.QuizRepository;
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
    QuestionRepository questionRepository;

    public void createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepository.save(quiz);
    }

    public List<QuestionWrapper> getQuizQuestions(int id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        List<Question> quizQuestions = quiz.get().getQuestions();
        List<QuestionWrapper> qustionsForUser = new ArrayList<>();
        for( Question q : quizQuestions){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            qustionsForUser.add(qw);
        }
        return qustionsForUser;
    }

    public int calculateResult(int id, List<Answer> answers) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        List<Question> quizQuestions = quiz.get().getQuestions();
        int right = 0;
        int i = 0;
        for( Answer answer : answers){
            if (answer.getAnswer().equals(quizQuestions.get(i).getRightAnswer())){
                right++;
            }
            i ++;
        }
        return right;
    }
}
