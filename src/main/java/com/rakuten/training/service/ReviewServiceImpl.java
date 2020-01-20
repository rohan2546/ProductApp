package com.rakuten.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rakuten.training.dal.ProductDAO;
import com.rakuten.training.dal.ReviewDAO;
import com.rakuten.training.domain.Product;
import com.rakuten.training.domain.Review;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    @Autowired
	ReviewDAO dao;
    @Autowired
    ProductDAO prodao;

  @Override
  public int addNewReview(Review review, int product_id) {
    Product p=prodao.findById(product_id);
    if(p!=null)
    {
    	review.setProduct(p);
    	Review r1=dao.save(review);
    	return r1.getId();
    }
    else
    {
    	throw new NoSuchProductException();
    }
  }

  @Override
  public void removeReview(int id) {
    if(dao.findById(id)!=null)
    { dao.deleteById(id);
    
    }
  }

  @Override
  public List<Review> findAll() {
    // TODO Auto-generated method stub
    return dao.findAll();
  }

  @Override
  public Review findById(int id) {
     if(dao.findById(id)!=null)
	  {return dao.findById(id);
	  }
     else
     {
    	 throw new NullPointerException();
     }
  }

  @Override
  public List<Review> findAll_By_ProductId(int product_id) {
    Product p=prodao.findById(product_id);
    if(p!=null)
    {
    	return dao.findAll_By_Product(product_id);
    }
    else
    {
    	throw new IllegalStateException("Product Not There");
    }
  }

  
 }
