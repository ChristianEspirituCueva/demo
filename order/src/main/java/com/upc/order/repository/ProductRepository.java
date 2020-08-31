package com.upc.order.repository;

import com.upc.order.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository//spring entiende que esta interfaz va a generar metodos de acceso a base de datos
public class ProductRepository extends JpaRepository<Product, Long> {

}
