package academy.digital.store.product.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import academy.digital.store.product.entity.Category;
import academy.digital.store.product.entity.Product;
import academy.digital.store.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service	@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
//	@Autowired
	private final ProductRepository productRepository;
	

	@Override
	public List<Product> listAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public Product getProduct(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public Product create(Product product) {
		System.out.println(product);
		product.setStatus("CREATED");
		product.setCreateAt(new Date());
		return productRepository.save(product);
	}

	@Override
	public Product update(Product product) {
		Product productDB = productRepository.findById(product.getId()).orElse(null);
		if(productDB == null) {
			return null;
		}
		return productRepository.save(product);
	}

	@Override
	public Product delete(Long id) {
		Product productDB = productRepository.findById(id).orElse(null);
		if(productDB != null) {
			productDB.setStatus("DELETED");
			return productRepository.save(productDB);
		}
		return null;
	}

	@Override
	public List<Product> findByCategory(Category category) {
		
		return productRepository.findByCategory(category);
	}

	@Override
	public Product updateStock(Long id, Double quantity) {
		Product productDB = productRepository.findById(id).orElse(null);
		if(productDB != null) {
			productDB.setStock( productDB.getStock() + quantity );
			return productRepository.save(productDB);
		}
		return null;
	}

}
