package academy.digital.store.product;

import java.util.Date;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import academy.digital.store.product.entity.Category;
import academy.digital.store.product.entity.Product;
import academy.digital.store.product.repository.ProductRepository;
import academy.digital.store.product.service.ProductService;
import academy.digital.store.product.service.ProductServiceImpl;

@SpringBootTest
public class ProductServiceMockTest {
	@Mock
	private ProductRepository productRepository;
	
	private ProductService productService;
	
	@BeforeEach
	public void setub() {
		MockitoAnnotations.initMocks(this);
		productService = new ProductServiceImpl(productRepository);
		
		Product product = Product.builder()
				.name("computer")
				.category(Category.builder().id(1L).build())
				.description("")
				.stock(Double.parseDouble("5"))
				.price(Double.parseDouble("12.5"))
				.createAt(new Date())
				.status("created")
				.build();
		
		Mockito.when(productRepository.findById(1L))
		.thenReturn(Optional.of(product));
		Mockito.when(productRepository.save(product)).thenReturn(product);
	}
	@Test
	public void whenValidGuetID_ThenReturnProduct() {
		Product found = productService.getProduct(1L);
		Assertions.assertThat(found.getName()).isEqualTo("computer");
	}
	@Test
	public void whenValidUpdateStock_TheReturnNewStock() {
		Product newStock = productService.updateStock(1L, Double.parseDouble("8"));
		Assertions.assertThat(newStock.getStock()).isEqualTo(13);
	}
}
