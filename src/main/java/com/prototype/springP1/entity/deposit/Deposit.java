package com.prototype.springP1.entity.deposit;

import com.prototype.springP1.entity.bank.Bank;
import com.prototype.springP1.entity.transaction.Transaction;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "bank", discriminatorType = DiscriminatorType.STRING)
public class Deposit implements Interface, Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getID()
    {
        return id;
    }

    @ManyToOne(targetEntity = Bank.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(insertable=false, updatable=false,referencedColumnName = "bank",name = "bank")
    private Bank bank;

    @Column(nullable=false, unique=true)
    private String card;

    @Column(nullable=false, updatable=false, unique=true)
    private String number;

    @Column(nullable=false)
    private long balance;

    @Column(nullable=false)
    private long interest;

    @Column(nullable=false)
    private String cvv2;

    @Temporal(TemporalType.DATE)
    @Column(nullable=false, updatable=false)
    private Date expiry;

    @Column(nullable=false)
    private String password;

    @Column
    private String second_password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sender")
    List<Transaction> sentTransaction;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "destination")
    List<Transaction> receivedTransaction;

    @Override
    public String getCard()
    {

        return card;

    }

    @Override
    public void setCard(String name)
    {

        this.card = name;

    }

    @Override
    public String getNumber()
    {

        return number;

    }

    @Override
    public void setNumber(String number)
    {

        this.number = number;

    }

    @Override
    public long getInterest()
    {

        return interest;

    }

    @Override
    public void setInterest(long rate)
    {

        this.interest = rate;

    }

    @Override
    public String getCvv2()
    {

        return cvv2;

    }

    @Override
    public void setCvv2(String cvv2)
    {

        this.cvv2 = cvv2;

    }

    @Override
    public Date getExpiry()
    {

        return expiry;

    }

    @Override
    public void setExpiry(Date expiry)
    {

        this.expiry = expiry;

    }

    @Override
    public String getPassword()
    {

        return password;

    }

    @Override
    public void setPassword(String password)
    {

        this.password = password;

    }

    @Override
    public long getBalance()
    {

        return balance;

    }

    @Override
    public void setBalance(long balance)
    {

        this.balance = balance;

    }

    @Override
    public String getSecond_password()
    {

        return second_password;

    }

    @Override
    public void setSecond_password(String second_password)
    {

        this.second_password = second_password;

    }

    @Override
    public void setSentTransaction(List<Transaction> sentTransaction)
    {

        this.sentTransaction = sentTransaction;

    }

    @Override
    public List<Transaction> getSentTransaction()
    {

        return this.sentTransaction;

    }

    @Override
    public void setReceivedTransaction(List<Transaction> receivedTransaction)
    {

        this.receivedTransaction = receivedTransaction;

    }

    @Override
    public List<Transaction> getReceivedTransaction()
    {

        return this.receivedTransaction;

    }

    @Override
    public long addBalance(long amount)
    {

        setBalance(getBalance()+amount);

        return amount;

    }

    @Override
    public long takeBalance(long amount)
    {

        if(amount>getBalance())
        {

            return 0;

        }

        setBalance(getBalance()-amount);

        return amount;

    }

    public Bank getBank() {
        return bank;
    }

}