package com.naveen.springbootmangodb.service;

import com.naveen.springbootmangodb.exception.TodoCollectionException;
import com.naveen.springbootmangodb.models.TodoDTO;
import com.naveen.springbootmangodb.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException,
            TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepository.findByTodo(todo.getTodo());
        if (todoOptional.isPresent()) {
            throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
        } else {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todo);
        }
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        List<TodoDTO> todos = todoRepository.findAll();
        if (todos.size() > 0) {
            return todos;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public TodoDTO getTodoById(String id) {
        Optional<TodoDTO> todoOptional = todoRepository.findById(id);
        return todoOptional.orElse(null);
    }

    @Override
    public Boolean deleteTodoById(String id) {
        TodoDTO toBeDeletedTodo = getTodoById(id);
        if (toBeDeletedTodo != null) {
            todoRepository.delete(toBeDeletedTodo);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public TodoDTO updateTodoById(String id, TodoDTO todo) {
        TodoDTO todoToSave = getTodoById(id);
        if (todoToSave != null) {
            todoToSave.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : todoToSave.getCompleted());
            todoToSave.setTodo(todo.getTodo() != null ? todo.getTodo() : todoToSave.getTodo());
            todoToSave.setDescription(todo.getDescription() != null ? todo.getDescription() : todoToSave.getDescription());
            todoToSave.setUpdateAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todoToSave);
            return todoToSave;
        } else {
            return null;
        }
    }

}
