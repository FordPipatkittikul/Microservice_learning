package com.question_service.controller;

import com.question_service.model.Answer;
import com.question_service.model.Question;
import com.question_service.model.QuestionWrapper;
import com.question_service.service.QuestionService;
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
    public ResponseEntity<List<Question>> HttpGetAllquestions(){
        try{
            return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("category/{category}")
    public  ResponseEntity<List<Question>> HttpGetQuestionsByCategory(@PathVariable String category){
        try{
            return new ResponseEntity<>(questionService.getQuestionsByCategory(category), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    @PostMapping("add")
    public ResponseEntity<String> HttpDddQuestion(@RequestBody Question question){
        try{
            questionService.addQuestion(question);
            return new ResponseEntity<>("added question successfully", HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("unable to add question", HttpStatus.BAD_REQUEST);
    }


    // api for quizservice to call

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> HttpGetQuestionIdsForQuiz(@RequestParam String category, @RequestParam int numQ){
        try{
            return new ResponseEntity<>(questionService.getQuestionIdsForQuiz(category, numQ), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> HttpGetQuestionsFromId(@RequestBody List<Integer> questionIds){
        return new ResponseEntity<>(questionService.getQuestionFromId(questionIds), HttpStatus.CREATED);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> HttpGetScore(@RequestBody List<Answer> answers){
        return new ResponseEntity<>(questionService.getScore(answers), HttpStatus.CREATED);
    }

}
