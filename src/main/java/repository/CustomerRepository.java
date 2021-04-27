package repository;

import model.Customer;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
public class CustomerRepository implements ICustomerRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Customer> findAll() {
        String query = "SELECT c FROM Customer AS c";
        List<Customer> customerList = entityManager.createQuery(query, Customer.class).getResultList();
        return customerList;
    }

    @Override
    public Customer findById(Long id) {
        String query = "SELECT c FROM Customer AS c WHERE c.id=:id";
        Customer customer= entityManager.createQuery(query,Customer.class).setParameter("id",id).getSingleResult();
        return customer;
    }

    @Override
    public void save(Customer customer) {
        if(customer!=null){
            entityManager.merge(customer);
        } else {
            entityManager.persist(customer);
        }
    }

    @Override
    public void delete(Long id) {
        Customer customer = findById(id);
        if(customer!=null){
            entityManager.remove(customer);
        }
    }
}
