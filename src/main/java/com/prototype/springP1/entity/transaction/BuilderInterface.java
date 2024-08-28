package com.prototype.springP1.entity.transaction;

import com.prototype.springP1.entity.deposit.Deposit;

public interface BuilderInterface
{

    void setDate();

    void setAmount(long amount);

    void setSender(Deposit sender);

    void setDestination(Deposit destination);

    void setSenderBalance(long senderBalance);

    void setDestinationBalance(long destinationBalance);

    void generateTID();

}