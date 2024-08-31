package com.prototype.springP1.entity.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getID()
    {
        return id;
    }

}
