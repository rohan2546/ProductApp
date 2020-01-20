package com.rakuten.training;

import java.util.Iterator;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.rakuten.training.dal.ProductDAO;
import com.rakuten.training.dal.ReviewDAO;
import com.rakuten.training.domain.Product;
import com.rakuten.training.domain.Review;
import com.rakuten.training.ui.ProductConsoleUI;

@SpringBootApplication
public class ProductAppApplication {

	public static void main(String[] args) {
		//ApplicationContext springContainer=
		SpringApplication.run(ProductAppApplication.class, args);
//		ProductConsoleUI ui=springContainer.getBean(ProductConsoleUI.class);
//		ui.createProductWithUI();
//		ui.findById();
//		ui.deleteproduct();
////		
//		ReviewDAO reviewDAO= springContainer.getBean(ReviewDAO.class);
//		Review sample=new Review("self","this is good",5);
//		Review saved= reviewDAO.save(sample,1);
//		System.out.println("Created review with id:"+saved.getId() ); 
		
//		ProductDAO productDAO=springContainer.getBean(ProductDAO.class);
//		Product p=productDAO.findById(1);
//		
//		productDAO.deleteById(1);
//		List<Product> p1=productDAO.findAll();
//		for(Product i:p1)
//		{
//			System.out.println(i.getName()+" "); 
//		}
//		System.out.println(p.getName());
//		System.out.println("This product has: "+p.getReviews().size()); 
	}

}
