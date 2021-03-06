package academy.digitalab.costumer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import academy.digitalab.costumer.repository.entity.Customer;
import academy.digitalab.costumer.repository.entity.Region;

public interface CustomerRepository  extends JpaRepository<Customer,Long> {
    public Customer findByNumberID(String numberID);
    public List<Customer> findByLastName(String lastName);
    public List<Customer> findByRegion(Region region);
}