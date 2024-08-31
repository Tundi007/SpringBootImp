package com.prototype.springP1.entity.transaction;

import com.prototype.springP1.entity.deposit.Deposit;
import com.prototype.springP1.entity.deposit.MelliDeposit;
import com.prototype.springP1.entity.deposit.SepahDeposit;
import com.prototype.springP1.repository.EntitySelect;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

public class Builder implements BuilderInterface
{

    public <T extends Deposit>Builder(Class<T> deposit)
    {

        this.deposit = deposit;

        if(deposit == MelliDeposit.class)
        {

            transaction = new MelliTransaction();

        }
        else
        if(deposit == SepahDeposit.class)
        {

            transaction = new SepahTransaction();

        }

    }

    private final Class<? extends Deposit> deposit;

    private Transaction transaction;

    @Override
    public void setSender(Deposit sender)
    {

        this.transaction.setSender(sender);

    }

    @Override
    public void setDestination(Deposit destination)
    {

        this.transaction.setDestination(destination);

    }

    @Override
    public void setSenderBalance(long balance)
    {

        this.transaction.setSender_Balance(balance);

    }

    @Override
    public void setDestinationBalance(long balance)
    {

        this.transaction.setDestination_Balance(balance);

    }

    @Override
    public void generateTID()
    {

        StringBuilder tID;

        do
        {

            tID = new StringBuilder();

            tID.append(new Random().nextInt( 9)).
                append(new Random().nextInt( 9)).
                append(new Random().nextInt( 9)).
                append(new Random().nextInt( 9)).
                append(new Random().nextInt( 9)).
                append(new Random().nextInt( 9)).
                append(new Random().nextInt( 9)).
                append(new Random().nextInt( 9)).
                append(new Random().nextInt( 9)).
                append(new Random().nextInt( 9)).
                append(new Random().nextInt( 9)).
                append(new Random().nextInt( 9));

        }while(EntitySelect.allTid(deposit).contains(tID.toString()));

        this.transaction.setTID(tID.toString());

    }

    @Override
    public void setDate()
    {

        this.transaction.setDate(Timestamp.valueOf(LocalDateTime.now()));

    }

    @Override
    public void setAmount(long amount)
    {

        this.transaction.setAmount(amount);

    }

    public Transaction getResult()
    {

        return this.transaction;

    }

}