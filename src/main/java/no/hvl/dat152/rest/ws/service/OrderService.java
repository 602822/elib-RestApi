/**
 *
 */
package no.hvl.dat152.rest.ws.service;

import java.util.List;

import java.time.LocalDate;
import java.util.Optional;

import no.hvl.dat152.rest.ws.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.OrderNotFoundException;
import no.hvl.dat152.rest.ws.model.Order;
import no.hvl.dat152.rest.ws.repository.OrderRepository;

/**
 * @author tdoy
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order saveOrder(Order order) {

        order = orderRepository.save(order);

        return order;
    }

    public void deleteOrder(Long id) throws OrderNotFoundException {

      Optional<Order> order = orderRepository.findById(id);
      if(order.isEmpty()) {
          throw new OrderNotFoundException("Order with id: " + id + "could not be found");
      }
      orderRepository.delete(order.get());

    }

    public List<Order> findAllOrders( ) {

        return orderRepository.findAll();
    }


    public List<Order> findByExpiryDate(LocalDate expiry, Pageable page) {


        Page<Order> orders = orderRepository.findByExpiryBefore(expiry, page);


        return orders.getContent();
    }

    public Order findOrder(Long id) throws OrderNotFoundException {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id: " + id + " not found in the order list!"));

        return order;
    }

    public Order updateOrder(Order order, Long id) throws OrderNotFoundException {


        Optional<Order> orderFromRepo = orderRepository.findById(id);

        if (orderFromRepo.isEmpty()) {
            throw new OrderNotFoundException("Order with id: " + id + " could not be found");
        }

        order.setId(id);
        orderRepository.save(order);


        return order;
    }
}
