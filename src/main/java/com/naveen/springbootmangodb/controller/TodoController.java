package com.naveen.springbootmangodb.controller;

import com.naveen.springbootmangodb.exception.TodoCollectionException;
import com.naveen.springbootmangodb.models.TodoDTO;
import com.naveen.springbootmangodb.repository.TodoRepository;
import com.naveen.springbootmangodb.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    @GetMapping("/todos")
    public ResponseEntity<?> getAll() {
        List<TodoDTO> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todos, todos.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todos")
    public ResponseEntity<?> create(@RequestBody TodoDTO todo) {
        try {
            todoService.createTodo(todo);
            return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingle(@PathVariable("id") String id) {
        TodoDTO todoDTO = todoService.getTodoById(id);
        if (todoDTO != null) {
            return new ResponseEntity<>(todoDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Todo not found with id " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody TodoDTO todo) {
        TodoDTO updatedTodo = todoService.updateTodoById(id, todo);
        if (updatedTodo != null) {
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Todo not found with id " + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        if (todoService.deleteTodoById(id)) {
            return new ResponseEntity<>("Successfully deleted with id " + id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("id " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
