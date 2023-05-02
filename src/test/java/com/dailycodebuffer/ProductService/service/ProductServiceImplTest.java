package com.dailycodebuffer.ProductService.service;

import com.dailycodebuffer.ProductService.entity.Product;
import com.dailycodebuffer.ProductService.exception.ProductServiceCustomException;
import com.dailycodebuffer.ProductService.model.ProductRequest;
import com.dailycodebuffer.ProductService.model.ProductResponse;
import com.dailycodebuffer.ProductService.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductRequest productRequest;
    private Product product;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        productRequest = new ProductRequest();
        productRequest.setName("Test Product");
        productRequest.setQuantity(10);
        productRequest.setPrice(100);

        product = new Product();
        product.setProductId(0L);
        product.setProductName("Test Product");
        product.setQuantity(10);
        product.setPrice(100);

        productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);
    }

    @Test
    void addProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        long productId = productService.addProduct(productRequest);

        assertEquals(productId, product.getProductId());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(1L);

        assertEquals(productResponse, response);
        verify(productRepository, times(1)).findById(1L);

        assertThrows(ProductServiceCustomException.class, () -> {
            productService.getProductById(2L);
        });
    }

    @Test
    void reduceQuantity() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.reduceQuantity(1L, 5L);

        assertEquals(product.getQuantity(), 5L);
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));

        assertThrows(ProductServiceCustomException.class, () -> {
            productService.reduceQuantity(1L, 10L);
        });
    }
}
