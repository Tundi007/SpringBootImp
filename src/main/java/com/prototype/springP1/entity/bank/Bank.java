package com.prototype.springP1.entity.bank;

import com.prototype.springP1.entity.deposit.Deposit;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "bank", discriminatorType = DiscriminatorType.STRING)
@Table(indexes = {@Index(name = "bank",columnList = "bank")})
public class Bank implements Interface, Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(updatable = false,insertable = false,name = "bank")
    private String bank;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "bank")
    List<Deposit> deposit;

    @Override
    public String getName()
    {

        return bank;

    }

    @Override
    public void setDeposit(List<Deposit> deposit)
    {

        this.deposit = deposit;

    }

    @Override
    public List<Deposit> getDeposit()
    {

        return deposit;

    }

}