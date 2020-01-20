package com.rakuten.training.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rakuten.training.domain.Review;
import com.rakuten.training.service.ReviewService;

@RestController
public class ReviewController {
	@Autowired ReviewService service;
	
	@GetMapping("/products/{pid}/reviews")
	public ResponseEntity<List<Review>> getAllReviews_ProdId(@PathVariable("pid") int id)
	{   try
	   { 
		return new ResponseEntity<List<Review>>(service.findAll_By_ProductId(id), HttpStatus.OK);
	   }
	catch(IllegalStateException e)
	{
		return new ResponseEntity<List<Review>>(HttpStatus.NOT_FOUND);
	}
	}
	
	@PostMapping("/products/{pid}/reviews")
	public ResponseEntity<List<Review>> addReview(@RequestBody Review reviewtoBeAdded,@PathVariable("pid") int pid)
	{
		try
		{
			service.addNewReview(reviewtoBeAdded, pid);
			return new ResponseEntity<List<Review>>(HttpStatus.CREATED);
		}
		catch(IllegalArgumentException e)
		{
			return new ResponseEntity<List<Review>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/reviews")
	public List<Review> ListReview()
	{
		
		return service.findAll();
	}
}
