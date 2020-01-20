package com.rakuten.training.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.Mockito;

import com.rakuten.training.dal.ProductDAO;
import com.rakuten.training.domain.Product;

public class ProductServiceImplTest {

  @Test
  public void addNewProduct_Returns_Valid_Id_When_ProductValue_GTEQ_MinValue() {
    // Arrange
    ProductServiceImpl service = new ProductServiceImpl();
    Product toBeAdded = new Product("tesk", 10000, 1); // notice 10000*1>=10000
    ProductDAO mockDAO = Mockito.mock(ProductDAO.class);
    Product saved = new Product("tesk", 10000, 1);
    saved.setId(1);
    Mockito.when(mockDAO.save(toBeAdded)).thenReturn(saved);
    service.setDao(mockDAO);
    // Act
    int id = service.addNewProduct(toBeAdded);
    // Assert
    assertTrue(id > 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNewProduct_Throws_When_ProductValue_LT_MinValue() {
    // Arrange
    ProductServiceImpl service = new ProductServiceImpl();
    Product toBeAdded = new Product("tesk", 9999, 1); // notice 9999*1>=9999
    // Act
    service.addNewProduct(toBeAdded);
    // Assert

  }
  @Test()
  public void testRemoveProduct_If_Value_Less_Than_MaxValue() {
    //Arrange
	ProductServiceImpl service=new ProductServiceImpl();
    Product toBeDeleted = new Product("test",9999,1);
    toBeDeleted.setId(1);
    ProductDAO mockDAO = Mockito.mock(ProductDAO.class);
    Mockito.when(mockDAO.findById(1)).thenReturn(toBeDeleted);
    service.setDao(mockDAO);
    int id=1;
    service.removeProduct(1);
    Mockito.verify(mockDAO).deleteById(id);   
  }
   @Test(expected = IllegalStateException.class)
   public void testDont_RemoveProduct_If_Value_GT_MaxValue()
   {
	   //Arrange
	   ProductServiceImpl service=new ProductServiceImpl();
	   Product toBeDeleted= new Product("test",10001,1);
	   toBeDeleted.setId(1);
	   ProductDAO mockDAO = Mockito.mock(ProductDAO.class);
	   Mockito.when(mockDAO.findById(1)).thenReturn(toBeDeleted);
	   service.setDao(mockDAO);
	   //int id=1;
       service.removeProduct(1);
   }
   @Test
   public void test_RemoveFunction_Does_Nothing_IfProduct_Not_Found()
   {   //Arrange
	  ProductServiceImpl service=new ProductServiceImpl();
	   Product toBeDeleted=null;
	   ProductDAO mockDAO = Mockito.mock(ProductDAO.class);
	   Mockito.when(mockDAO.findById(1)).thenReturn(toBeDeleted);
	   service.setDao(mockDAO);
	   service.removeProduct(1);
	   //assertNull(toBeDeleted);
	   
   }
  
}
