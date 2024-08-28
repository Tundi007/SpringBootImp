package com.prototype.springP1.service;

import com.prototype.springP1.Application;
import com.prototype.springP1.entity.deposit.Deposit;
import com.prototype.springP1.entity.transaction.Builder;
import com.prototype.springP1.entity.transaction.Directory;
import com.prototype.springP1.entity.transaction.Transaction;
import com.prototype.springP1.repository.EntityManagement;

public class MakeTransaction
{

    private static Transaction make(Deposit from, Deposit to, long amount)
    {

        try(EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            Directory transactionDir = new Directory();

            Builder transactionB;

            if (from == null)
            {

                transactionB = new Builder(to.getClass());

            } else {

                transactionB = new Builder(from.getClass());

            }

            transactionDir.make(transactionB, from, to, amount);

            Transaction transaction = transactionB.getResult();

            entityManagement.persist(transaction);

            if (from == null)
            {

                entityManagement.merge(to);

            } else
            if (to == null)
            {

                entityManagement.merge(from);

            }else
            {

                entityManagement.merge(from);

                entityManagement.merge(to);

            }

            entityManagement.flush();

            entityManagement.clear();

            return transaction;

        }

    }

    public static Transaction takeBalance(Deposit from, long amount)
    {

        try(EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            from = entityManagement.find(from.getID(), from.getClass());

        }

        return make(from, null, from.takeBalance(amount));

    }

    public static Transaction addBalance(Deposit to, long amount)
    {

        try(EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            to = entityManagement.find(to.getID(), to.getClass());

        }

        return make(null,to,to.addBalance(amount));

    }

    public static Transaction cardToCard(Deposit from, Deposit to, long amount)
    {

        try(EntityManagement entityManagement = Application.context.getBean(EntityManagement.class)) {

            to = entityManagement.find(to.getID(), Deposit.class);

            from = entityManagement.find(from.getID(), Deposit.class);

        }

        return make(from, to, to.addBalance(from.takeBalance(amount)));

    }



}
