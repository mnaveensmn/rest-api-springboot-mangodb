package com.naveen.springbootmangodb.service;

import com.naveen.springbootmangodb.exception.TodoCollectionException;
import com.naveen.springbootmangodb.models.TodoDTO;

import java.util.List;


public interface TodoService {

    public void createTodo(TodoDTO todo) throws TodoCollectionException;

    public List<TodoDTO> getAllTodos();

    public TodoDTO getTodoById(String id);

    public Boolean deleteTodoById(String id);

    public TodoDTO updateTodoById(String id,TodoDTO todoDTO);

}
