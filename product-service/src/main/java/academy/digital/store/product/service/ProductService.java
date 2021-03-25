package academy.digital.store.product.service;

import java.util.List;

import academy.digital.store.product.entity.Category;
import academy.digital.store.product.entity.Product;

public interface ProductService {
	public List<Product> listAllProduct();
	public Product getProduct(Long id);
	
	public Product create(Product product);
	public Product update(Product product);
	public Product delete(Long id);
	public List<Product> findByCategory(Category category);
	public Product updateStock(Long id,Double quantity);
	
}
