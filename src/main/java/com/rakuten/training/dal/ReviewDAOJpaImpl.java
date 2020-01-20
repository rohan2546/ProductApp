package com.rakuten.training.dal;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rakuten.training.domain.Product;
import com.rakuten.training.domain.Review;

@Repository
@Transactional
public class ReviewDAOJpaImpl implements ReviewDAO 
{
    @Autowired
	EntityManager em;	
    
    @Override
  public Review findById(int id)
    {   // Product p=em.find(Product.class,id);
    //System.out.println("This product has: "+p.getReviews().size());
    	return em.find(Review.class, id);
    }
    @Override
  public Review save(Review toBeSaved)
    {   
    	em.persist(toBeSaved);
    	return toBeSaved;
    }
    @Override
  public void deleteById(int id)
    {
    	Review r=em.find(Review.class, id);
    	em.remove(r);
    }
    @Override
  public List<Review> findAll()
    {
    	 Query q=em.createQuery("select p from Review as p");
    	    List<Review> all=q.getResultList();
    	    return all;
    }
  @Override
  public List<Review> findAll_By_Product(int productId) {
    Product p=em.find(Product.class, productId);
    if(p!=null)
    {
    	TypedQuery<Review> q=em.createQuery("select r from Review as r where r.product.id=:param", Review.class);
    	q.setParameter("param", productId);
        List<Review> all=q.getResultList();
        return all;
    }
    else
    {
    	throw new IllegalStateException("Product doesn't exist");
    }
    
  }
}
