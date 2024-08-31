package com.prototype.springP1.entity.transaction;

import com.prototype.springP1.entity.deposit.Deposit;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "bank", discriminatorType = DiscriminatorType.STRING)
public class Transaction implements Interface, Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(updatable = false,nullable = false,unique = true)
    private String tID;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false,updatable = false)
    private Timestamp date;

    @Column(nullable = false,updatable = false)
    private long amount;

    @Column(nullable = false,updatable = false)
    private long sender_balance;

    @Column(nullable = false,updatable = false)
    private long destination_balance;

    @ManyToOne(targetEntity = Deposit.class,fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(updatable = false, referencedColumnName = "card",name = "sender")
    private Deposit sender;

    @ManyToOne(targetEntity = Deposit.class,fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(updatable = false, referencedColumnName = "card", name = "destination")
    private Deposit destination;

    @Override
    public Timestamp getDate()
    {

        return date;

    }

    @Override
    public void setDate(Timestamp date)
    {

        this.date = date;

    }

    @Override
    public long getAmount()
    {

        return amount;

    }

    @Override
    public void setAmount(long amount)
    {

        this.amount = amount;

    }

    @Override
    public Deposit getSender()
    {

        return sender;

    }

    @Override
    public void setSender(Deposit senderCard)
    {

        this.sender = senderCard;

    }

    @Override
    public Deposit getDestination()
    {

        return destination;

    }

    @Override
    public void setDestination(Deposit destination)
    {

        this.destination = destination;

    }

    @Override
    public long getSender_Balance()
    {

        return sender_balance;

    }

    @Override
    public void setSender_Balance(long sender_balance)
    {

        this.sender_balance = sender_balance;

    }

    @Override
    public long getDestination_Balance()
    {

        return destination_balance;

    }

    @Override
    public void setDestination_Balance(long destination_balance)
    {

        this.destination_balance = destination_balance;

    }

    @Override
    public String getTID()
    {

        return tID;

    }

    @Override
    public void setTID(String transactionId)
    {

        this.tID = transactionId;

    }

}