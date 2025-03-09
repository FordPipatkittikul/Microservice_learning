package com.question_service.service;

import com.question_service.model.Answer;
import com.question_service.model.Question;
import com.question_service.model.QuestionWrapper;
import com.question_service.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionRepository.findByCategory(category);
    }

    public void addQuestion(Question question) {
        questionRepository.save(question);
    }





    public List<Integer> getQuestionIdsForQuiz(String category, int numQ) {
        List<Integer> questions = questionRepository.findRandomQuestionsByCategory(category, numQ);
        return questions;
    }

    public List<QuestionWrapper> getQuestionFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for(Integer id : questionIds){
            questions.add(questionRepository.findById(id).get());
        }

        for(Question question : questions){
            QuestionWrapper wrapper = new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
            wrappers.add(wrapper);
        }
        return wrappers;
    }

    public Integer getScore(List<Answer> answers) {
        int right = 0;
        for( Answer answer : answers){
            Question question = questionRepository.findById(answer.getId()).get();
            if (answer.getAnswer().equals(question.getRightAnswer())){
                right++;
            }
        }
        return right;
    }
}
