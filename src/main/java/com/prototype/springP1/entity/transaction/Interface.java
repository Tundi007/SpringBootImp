package com.prototype.springP1.entity.transaction;

import com.prototype.springP1.entity.deposit.Deposit;

import java.sql.Timestamp;

public interface Interface
{

    Timestamp getDate();

    void setDate(Timestamp date);

    long getAmount();

    void setAmount(long amount);

    com.prototype.springP1.entity.deposit.Interface getSender();

    void setSender(Deposit sender);

    com.prototype.springP1.entity.deposit.Interface getDestination();

    void setDestination(Deposit destination);

    long getSender_Balance();

    void setSender_Balance(long sender_balance);

    long getDestination_Balance();

    void setDestination_Balance(long updatedBalanceDestination);

    String getTID();

    void setTID(String transactionId);

}
