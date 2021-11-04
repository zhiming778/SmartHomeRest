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
import com.jimmy.service.DeviceService;
import com.jimmy.util.Messages;
import com.jimmy.vo.Device;
import com.jimmy.vo.ExceptionResponse;
import com.jimmy.vo.MessageResponse;
import com.jimmy.vo.PagedResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Validated
@RestController
@RequestMapping(value = "/api")
@Api(description = "Device Api")
public class DeviceController {

    private static final Logger log = LoggerFactory.getLogger(DeviceController.class);

    private DeviceService deviceService;

    private Messages messages;

    @Autowired
    public DeviceController(DeviceService deviceService, Messages messages) {
        this.deviceService = deviceService;
        this.messages = messages;
    }

    @ApiOperation("Get paginated devices.")
    @RequestMapping(value = "/device", method = RequestMethod.GET)
    public ResponseEntity<PagedResponse<Device>> getPaginatedDevices(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "6") int rows,
            @RequestParam(required = false, defaultValue = "name") String order) {
        PagedResponse<Device> body = deviceService.findPaginateDevices(page, rows, order);
        return ResponseEntity.ok(body);
    }

    @ApiOperation("Get a device by id.")
    @RequestMapping(value = "/device/{id}", method = RequestMethod.GET)
    public ResponseEntity<Device> getDeviceById(@Min(1) @PathVariable Long id) throws ResourceNotFoundException {
        Device device = deviceService.findById(id);
        if (device == null)
            throw new ResourceNotFoundException(String.valueOf(id));
        return ResponseEntity.ok(device);
    }

    @ApiOperation("Add a new device.")
    @RequestMapping(value = "/device", method = RequestMethod.POST)
    public ResponseEntity<MessageResponse<Device>> addDevice(@Valid @RequestBody Device device)
            throws WrongForeignKeyException {
        Device savedDevice = deviceService.saveDevice(device);
        if (savedDevice == null)
            throw new WrongForeignKeyException();
        MessageResponse<Device> body = new MessageResponse<Device>(messages.get("RESOURCE_CREATED"), savedDevice);
        return new ResponseEntity<MessageResponse<Device>>(body, HttpStatus.CREATED);
    }

    @ApiOperation("update the information of an existing deivce.")
    @RequestMapping(value = "/device/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MessageResponse<Device>> updateDevice(@PathVariable Long id,
            @Valid @RequestBody Device device) throws WrongForeignKeyException {
        Device updatedDevice = deviceService.updateDevice(id, device);
        if (updatedDevice == null)
            throw new WrongForeignKeyException();
        MessageResponse<Device> body = new MessageResponse<Device>(messages.get("RESOURCE_UPDATED"), updatedDevice);
        return ResponseEntity.ok(body);
    }

    @ApiOperation("Delete an existing device.")
    @RequestMapping(value = "/device/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MessageResponse<Device>> deleteDevice(@Min(1) @PathVariable Long id)
            throws ResourceNotFoundException {
        Device deletedDevice = deviceService.deleteById(id);
        if (deletedDevice == null)
            throw new ResourceNotFoundException(String.valueOf(id));
        MessageResponse<Device> body = new MessageResponse<Device>(messages.get("RESOURCE_DELETED"), deletedDevice);
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
