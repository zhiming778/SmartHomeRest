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
import com.jimmy.service.BrandService;
import com.jimmy.util.Messages;
import com.jimmy.vo.Brand;
import com.jimmy.vo.ExceptionResponse;
import com.jimmy.vo.MessageResponse;
import com.jimmy.vo.PagedResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Brand Api")
@Validated
@RestController
@RequestMapping("/api")
public class BrandController {

    private static final Logger log = LoggerFactory.getLogger(BrandController.class);

    private BrandService brandService;

    private Messages messages;

    @Autowired
    public BrandController(BrandService brandService, Messages messages) {
        this.brandService = brandService;
        this.messages = messages;
    }

    @ApiOperation("Get paginated Brands.")
    @RequestMapping(value = "/brand", method = RequestMethod.GET)
    public ResponseEntity<PagedResponse<Brand>> getPaginatedBrands(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int rows,
            @RequestParam(required = false, defaultValue = "name") String order) {
        PagedResponse<Brand> body = brandService.findPaginatedBrands(page, rows, order);
        return ResponseEntity.ok(body);
    }

    @ApiOperation("Get a brand by id.")
    @RequestMapping(value = "/brand/{id}", method = RequestMethod.GET)
    public ResponseEntity<Brand> getBrandById(@Min(1) @PathVariable Long id) throws ResourceNotFoundException {
        Brand brand = brandService.findById(id);
        if (brand == null)
            throw new ResourceNotFoundException(String.valueOf(id));
        return ResponseEntity.ok(brand);
    }

    @ApiOperation("Add a new brand.")
    @RequestMapping(value = "/brand", method = RequestMethod.POST)
    public ResponseEntity<MessageResponse<Brand>> addBrand(@Valid @RequestBody Brand brand) {
        Brand savedBrand = brandService.saveBrand(brand);
        MessageResponse<Brand> body = new MessageResponse<Brand>(messages.get("RESOURCE_CREATED"), savedBrand);
        return new ResponseEntity<MessageResponse<Brand>>(body, HttpStatus.CREATED);
    }

    @ApiOperation("Update a brand.")
    @RequestMapping(value = "/brand/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MessageResponse<Brand>> updateBrand(@PathVariable Long id, @Valid @RequestBody Brand brand)
            throws ResourceNotFoundException {
        Brand updatedBrand = brandService.updateBrand(id, brand);
        if (updatedBrand == null)
            throw new ResourceNotFoundException(String.valueOf(id));
        MessageResponse<Brand> body = new MessageResponse<Brand>(messages.get("RESOURCE_UPDATED"), updatedBrand);
        return ResponseEntity.ok(body);
    }

    @ApiOperation("Delete a brand.")
    @RequestMapping(value = "/brand/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MessageResponse<Brand>> deleteBrand(@Min(1) @PathVariable Long id)
            throws ResourceNotFoundException {
        Brand deletedBrand = brandService.deleteBrand(id);
        if (deletedBrand == null)
            throw new ResourceNotFoundException(String.valueOf(id));
        MessageResponse<Brand> body = new MessageResponse<Brand>(messages.get("RESOURCE_DELETED"), deletedBrand);
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
