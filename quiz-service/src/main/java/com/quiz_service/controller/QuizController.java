package com.quiz_service.controller;

import com.quiz_service.model.Answer;
import com.quiz_service.model.QuizDto;
import com.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;


    @PostMapping("create")
    public ResponseEntity<Object> createQuiz(@RequestBody QuizDto quizDto){
        try{
            quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle());
            return  new ResponseEntity<>("created quiz successfully", HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new ResponseEntity<>("unable to create quiz successfully", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("get/{id}")
    // @PathVariable is for GET /get/1
    public ResponseEntity<Object> getQuizQuestions(@PathVariable int id){
        try{
            return  new ResponseEntity<>(quizService.getQuizQuestions(id), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new ResponseEntity<>("unable to get quizQuestions", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Object> submitQuiz(@PathVariable int id, @RequestBody List<Answer> answers){
        try{
            return  new ResponseEntity<>(quizService.calculateResult(id, answers), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }

        return  new ResponseEntity<>("unable to get quizQuestions", HttpStatus.BAD_REQUEST);
    }

}
