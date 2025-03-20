package com.course.springboot.entities;

import com.course.springboot.entities.pk.OrderItemPK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_order_item")
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem implements Serializable {
    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();

    @Setter
    @Getter
    private int quantity;
    @Setter
    @Getter
    private double price;


    public OrderItem(Order order, Product product, int quantity, double price) {
        super();
        id.setOrder(order);
        id.setProduct(product);
        this.quantity = quantity;
        this.price = price;
    }

    @JsonIgnore
    public Order getOrder(){
        return id.getOrder();
    }
    public void setOrder(Order order){
        id.setOrder(order);
    }

    public Product getProduct(){
        return id.getProduct();
    }
    public void setProduct(Product product){
        id.setProduct(product);
    }

    public Double getSubTotal(){
        return price*quantity;
    }


}
