package com.rakuten.training.service;

import java.util.List;

import com.rakuten.training.domain.Review;

public interface ReviewService {
	int addNewReview(Review review, int product_id);
	
	void removeReview(int id);
	
	List<Review> findAll();
	
	Review findById(int id);
	
	List<Review> findAll_By_ProductId(int product_id);
}
