package academy.digital.store.product;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import academy.digital.store.product.entity.Category;
import academy.digital.store.product.entity.Product;
import academy.digital.store.product.repository.ProductRepository;

@DataJpaTest
public class ProductRepositoryMockTest {
	@Autowired
	private ProductRepository productRepository;
	@Test
	public void whenFindByCategory_thenReturnListProduct() {
		Product product = Product.builder()
				.name("computer")
				.category(Category.builder().id(1L).build())
				.description("")
				.stock(Double.parseDouble("10"))
				.price(Double.parseDouble("12"))
				.createAt(new Date())
				.status("created")
				.build();
		productRepository.save(product);
		
		List<Product> founds = productRepository.findByCategory(product.getCategory());
		Assertions.assertThat(founds.size()).isEqualTo(3);
	}

}
