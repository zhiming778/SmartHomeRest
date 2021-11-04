package com.jimmy.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jimmy.exception.ResourceNotFoundException;
import com.jimmy.exception.WrongForeignKeyException;
import com.jimmy.service.TypeService;
import com.jimmy.util.Messages;
import com.jimmy.vo.ExceptionResponse;
import com.jimmy.vo.MessageResponse;
import com.jimmy.vo.PagedResponse;
import com.jimmy.vo.Type;

import io.swagger.annotations.Api;

@Api(description = "Type Api")
@Validated
@RestController
@RequestMapping("/api")
public class TypeController {

    private static final Logger log = LoggerFactory.getLogger(TypeController.class);

    private TypeService typeService;
    private Messages messages;

    @Autowired
    public TypeController(TypeService typeService, Messages messages) {
        this.typeService = typeService;
        this.messages = messages;
    }

    @RequestMapping(value = "/type", method = RequestMethod.GET)
    public ResponseEntity<PagedResponse<Type>> getPaginatedTypes(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int rows,
            @RequestParam(required = false, defaultValue = "name") String order) {
        PagedResponse<Type> body = typeService.findPaginatedTypes(page, rows, order);
        return ResponseEntity.ok(body);
    }

    @RequestMapping(value = "/type/{id}", method = RequestMethod.GET)
    public ResponseEntity<Type> getTypeById(@Min(1) @PathVariable Long id) throws ResourceNotFoundException {
        Type type = typeService.findById(id);
        if (type == null)
            throw new ResourceNotFoundException(String.valueOf(id));
        return ResponseEntity.ok(type);
    }

    @RequestMapping(value = "/type", method = RequestMethod.POST)
    public ResponseEntity<MessageResponse<Type>> addType(@Valid @RequestBody Type type) {
        Type savedType = typeService.saveType(type);
        MessageResponse<Type> body = new MessageResponse<Type>(messages.get("RESOURCE_CREATED"), savedType);
        return new ResponseEntity<MessageResponse<Type>>(body, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/type/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MessageResponse<Type>> updateType(@PathVariable Long id, @Valid @RequestBody Type type)
            throws ResourceNotFoundException {
        Type updatedType = typeService.updateType(id, type);
        if (updatedType == null)
            throw new ResourceNotFoundException(String.valueOf(id));
        MessageResponse<Type> body = new MessageResponse<Type>(messages.get("RESOURCE_UPDATED"), updatedType);
        return ResponseEntity.ok(body);
    }

    @RequestMapping(value = "/type/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MessageResponse<Type>> deleteType(@Min(1) @PathVariable Long id)
            throws ResourceNotFoundException {
        Type deletedType = typeService.deleteType(id);
        if (deletedType == null)
            throw new ResourceNotFoundException(String.valueOf(id));
        MessageResponse<Type> body = new MessageResponse<Type>(messages.get("RESOURCE_DELETED"), deletedType);
        return ResponseEntity.ok(body);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleDeviceNotFoundException(ResourceNotFoundException e) {
        String msg = messages.get("RESOURCE_NOT_FOUND");
        log.error(msg);
        ExceptionResponse body = new ExceptionResponse(msg, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(WrongForeignKeyException.class)
    public ResponseEntity<ExceptionResponse> handleWrongForeignKeyException(WrongForeignKeyException e) {
        String msg = "The foreign key doesn't exist.";
        log.error(msg);
        ExceptionResponse body = new ExceptionResponse(msg, HttpStatus.UNPROCESSABLE_ENTITY.value());
        return ResponseEntity.unprocessableEntity().body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = messages.get("INVALID_ARGUMENTS");
        log.error(msg);
        ExceptionResponse body = new ExceptionResponse(msg, HttpStatus.UNPROCESSABLE_ENTITY.value());
        return ResponseEntity.unprocessableEntity().body(body);
    }
}
