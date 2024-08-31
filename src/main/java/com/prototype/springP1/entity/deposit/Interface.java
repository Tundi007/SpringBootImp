package com.prototype.springP1.entity.deposit;

import com.prototype.springP1.entity.transaction.Transaction;

import java.sql.Date;
import java.util.List;

public interface Interface
{

    String getCard();

    void setCard(String name);

    String getNumber();

    void setNumber(String number);

    long getInterest();

    void setInterest(long rate);

    String getCvv2();

    void setCvv2(String cvv2);

    Date getExpiry();

    void setExpiry(Date expiry);

    String getPassword();

    void setPassword(String password);

    long getBalance();

    void setBalance(long balance);

    String getSecond_password();

    void setReceivedTransaction(List<Transaction> receivedTransaction);

    List<Transaction> getReceivedTransaction();

    void setSentTransaction(List<Transaction> sentTransaction);

    List<Transaction> getSentTransaction();

    void setSecond_password(String second_password);

    long addBalance(long amount);

    long takeBalance(long amount);

}
