package com.example.demo.controller;

import com.example.demo.model.Answer;
import com.example.demo.service.QuizService;
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

    // ex: http://localhost:8080/quiz/create?category=Java&numQ=5&title=Jquiz
    // @RequestParam  for GET /get?id=1
    @PostMapping("create")
    public ResponseEntity<Object> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
        try{
            System.out.println(numQ);
            System.out.println(title);
            quizService.createQuiz(category, numQ, title);
            return  new ResponseEntity<>("created quiz successfully", HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new ResponseEntity<>("unable to create quiz successfully", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("get/{id}")
    // @PathVariable is for GET /get/1
    public ResponseEntity<Object> getQuizQuestions(@PathVariable  int id){
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
