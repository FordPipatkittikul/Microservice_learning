package com.example.demo.controller;

import com.example.demo.model.Question;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<Object> getAllquestions(){
        try{
            return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("category/{category}")
    public  ResponseEntity<Object> getQuestionsByCategory(@PathVariable String category){
        try{
            return new ResponseEntity<>(questionService.getQuestionsByCategory(category), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    @PostMapping("add")
    public ResponseEntity<Object> addQuestion(@RequestBody Question question){
        try{
            questionService.addQuestion(question);
            return new ResponseEntity<>("added question successfully", HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("unable to add question", HttpStatus.BAD_REQUEST);
    }
}
