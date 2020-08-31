package com.upc.order.model;

import lombok.*;
//spring data jpa abstrae a una base de datos con la que estas trabajando, pero no se amarra a esa base de datos, por lo que puedes cambiarlo o migrarlo a otra base de datos(ejem: postgres -> mysql)
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor//constructor con argumentos
@NoArgsConstructor
@Builder
//cuando se tenga un objeto de producto, no es necesario que se tenga que crear con todos sus atributos, sino puede que solo utilices el nombre del producto y pues solo te retorna ese//cuando se tiene un objeto completo porque esta conformado por muchos atributos, por ejemplo atributos basicos o complejos, donde en la estructura de un objeto se tenga otro objeto
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 60)
    private String name;
    @Column(name = "price", nullable = false)
    private Double price;
}
