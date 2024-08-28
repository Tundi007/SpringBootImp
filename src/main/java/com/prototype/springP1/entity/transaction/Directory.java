package com.prototype.springP1.entity.transaction;

import com.prototype.springP1.entity.deposit.Deposit;

public class Directory
{

    public void make(BuilderInterface builder, Deposit sender,
                     Deposit destination, long amount)
    {

        builder.generateTID();

        builder.setAmount(amount);

        builder.setDate();

        if(sender != null)
        {

            builder.setSender(sender);

            builder.setSenderBalance(sender.getBalance());

        }

        if(destination != null)
        {

            builder.setDestination(destination);

            builder.setDestinationBalance(destination.getBalance());

        }

    }

}