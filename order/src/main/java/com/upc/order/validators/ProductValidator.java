package com.upc.order.validators;


import com.upc.order.exceptions.ValidateServiceException;
import com.upc.order.model.Product;

public class ProductValidator {

    public static void save(Product product) {

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new ValidateServiceException("El nombre es requerido");
        }

        if (product.getName().length() > 100) {
            throw new ValidateServiceException("El nombre es muy largo (max 100)");
        }

        if (product.getPrice() == null) {
            throw new ValidateServiceException("El precio es requerido");
        }

        if (product.getPrice() < 0) {
            throw new ValidateServiceException("El precio es incorrecto");
        }
    }
}