package com.rakuten.training.web;

import java.net.http.HttpResponse;

import org.assertj.core.condition.AnyOf;
import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakuten.training.domain.Product;
import com.rakuten.training.service.ProductService;

import ch.qos.logback.core.status.Status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerUnitTest {
  @Autowired MockMvc mockMvc;

  @MockBean ProductService service;

  @Test
  public void getProductById_Returns_OK_With_Correct_Product_If_Found() throws Exception {
    // Arrange
    Product found = new Product("test", 123.0f, 100);
    int id = 1;
    found.setId(id);
    Mockito.when(service.findById(id)).thenReturn(found);
    // Act and Assert
    mockMvc
        .perform(MockMvcRequestBuilders.get("/products/{id}", id))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(id)));
  }

  @Test
  public void getProductById_Returns_NOT_FOUND_If_Not_Found() throws Exception {
    Product notFound = new Product("test", 123.0f, 100);
    int id = 1;
    notFound.setId(id);
    Mockito.when(service.findById(id)).thenReturn(null);
    mockMvc
        .perform(MockMvcRequestBuilders.get("/products/{id}", id))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void addProduct_If_Buisness_Logic_Is_Matched() throws Exception {
    Product toBeAdded = new Product("test", 10000.0f, 123);
    int id = 1;
    Mockito.when(service.addNewProduct(Mockito.any(Product.class))).thenReturn(id);
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(toBeAdded);
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, "/products" + id))
        .andExpect(MockMvcResultMatchers.status().isCreated());
    Mockito.verify(service).addNewProduct(Mockito.any(Product.class));
  }

  @Test
  public void addProduct_Fails_If_Buisness_Logic_Fails() throws Exception {
    Product nottoBeAdded = new Product("test", 12, 1);
    Mockito.when(service.addNewProduct(Mockito.any(Product.class)))
        .thenThrow(IllegalArgumentException.class);
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(nottoBeAdded);
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    Mockito.verify(service).addNewProduct(Mockito.any(Product.class));
  }

  @Test
  public void delete_Product_If_Buisness_Logic_Passes() throws Exception {
    // Product toBeDeleted=new Product("tets",12000.0f,1);
    int id = 1;
    Mockito.doNothing().when(service).removeProduct(id);
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/products/{id}"))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
    Mockito.verify(service).removeProduct(id);
  }

  @Test
  public void delete_Product_If_Buisness_Logic_Fails_And_Product_Is_There() throws Exception {
    int id = 1;
    Mockito.doThrow(IllegalStateException.class).when(service).removeProduct(id);
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/products/{id}"))
        .andExpect(MockMvcResultMatchers.status().isConflict());
    Mockito.verify(service).removeProduct(id);
  }

  @Test
  public void delete_Product_If_Product_Is_Not_There() throws Exception {
    int id = 1;
    Mockito.doThrow(NullPointerException.class).when(service).removeProduct(id);
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/products/{id}"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    Mockito.verify(service).removeProduct(id);
  }
}
