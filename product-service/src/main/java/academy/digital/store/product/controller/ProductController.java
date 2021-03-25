package academy.digital.store.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import academy.digital.store.product.entity.Category;
import academy.digital.store.product.entity.Product;
import academy.digital.store.product.service.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<Product>> listProduct(@RequestParam(name = "categoryId",required = false) Long categoryId){
		List<Product> products = new ArrayList<Product>();
		if(null == categoryId) {
			products = productService.listAllProduct();
		}else {
			products = productService.findByCategory(Category.builder().id(categoryId).build());
		}
		if(!products.isEmpty()) {
			return ResponseEntity.ok(products);
		}
		return ResponseEntity.notFound().build();
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
		Product product = productService.getProduct(id);
		if(product == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(product);
	}
	
	@PostMapping
	public ResponseEntity<Product> create(@Valid @RequestBody Product product, BindingResult result){
		if(result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
		}
		
		Product productCreated = productService.create(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
	}
	
	@PostMapping(value = "/{id}")
	public ResponseEntity<Product> update(@PathVariable("id") Long id, @RequestBody Product product){
		product.setId(id);
		Product productDB = productService.update(product);
		if(productDB == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(productDB);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Product> delete(@PathVariable("id") Long id){
		Product productDelete = productService.delete(id);
		if(productDelete == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productDelete);
	}
	
	@GetMapping(value = "/{id}/stock")
	public ResponseEntity<Product> updateStock(@PathVariable("id") Long id, 
			@RequestParam(name = "quantity",required = true) Double quantity){
		Product productDB = productService.updateStock(id, quantity);
		if(productDB == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productDB);
	}
	
	private String formatMessage(BindingResult result) {
		List<Map<String, String>> errors = result.getFieldErrors().stream()
				.map(err->{
					Map<String,String> error = new HashMap<String, String>();
						error.put(err.getField(), err.getDefaultMessage());
						return error;
					}).collect(Collectors.toList());
		
		ErrorMessage errorMessage  = ErrorMessage.builder().code("01").messages(errors).build();
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(errorMessage);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

}
