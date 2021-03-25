package academy.digital.store.product.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import academy.digital.store.product.entity.Category;
import academy.digital.store.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	public List<Product> findByCategory(Category category);
}
