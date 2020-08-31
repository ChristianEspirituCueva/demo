package com.upc.order.service;

import com.upc.order.exceptions.GeneralServiceException;
import com.upc.order.exceptions.NoDataFoundException;
import com.upc.order.exceptions.ValidateServiceException;
import com.upc.order.model.Product;
import com.upc.order.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upc.order.validators.*;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;


@Slf4j//manejo de log, para que los errores salgan en consola
@Service
public class ProductService {
    //Su funcion de service no es crear objetos sino utilizarlos
    @Autowired//en este punto Service necesita esta dependencia
            ProductRepository productRepository;

    public Product getProductById(Long productId) {
        try {
            log.debug("findById => " + productId);
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NoDataFoundException("No existe el producto"));
            return product;
        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    @Transactional
//se utiliza en este caso en alguno de los metodos, es que a nivel de base de datos ssabemos que cuando se tenga que ejecutar un insert o un update o delete, estas operaciones mientras se ejecutan hacen inconsistencia en siertos datos de las tablas, entonces cuando se terminana de ejecutar lo que deseamos es que se hacen un commit y el rolbar,
    public void deleteProduct(Long productId) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NoDataFoundException("No existe el producto"));
            productRepository.delete(product);
        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }

    }

    public List<Product> getAllProducts(Pageable page) {
        try {
            List<Product> products = productRepository.findAll(page).toList();
            return products;
        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    public Product saveProduct(Product product) {
        try {
            ProductValidator.save(product);

            if (product.getId() == null) {
                Product newProduct = productRepository.save(product);
                return newProduct;
            }

            Product exitProduct = productRepository.findById(product.getId())
                    .orElseThrow(() -> new NoDataFoundException("No existe el producto"));

            exitProduct.setName(product.getName());
            exitProduct.setPrice(product.getPrice());

            productRepository.save(exitProduct);

            return exitProduct;
        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }

    }
}
