package com.prototype.springP1.repository;

import com.prototype.springP1.Application;
import com.prototype.springP1.entity.bank.Bank;
import com.prototype.springP1.entity.deposit.Deposit;
import com.prototype.springP1.entity.deposit.Interface;
import com.prototype.springP1.entity.deposit.MelliDeposit;
import com.prototype.springP1.entity.deposit.SepahDeposit;
import com.prototype.springP1.entity.transaction.MelliTransaction;
import com.prototype.springP1.entity.transaction.SepahTransaction;
import com.prototype.springP1.entity.transaction.Transaction;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public abstract class EntitySelect
{

    public static List<Deposit> allDeposit()
    {

        try(EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            EntityManagement.QBuilder<Deposit> qBuilder = entityManagement.QueryBuilder();

            Root<Deposit> root = qBuilder.criteriaQuery.from(Deposit.class);

            qBuilder.criteriaQuery.select(root);

            return qBuilder.getList();

        }

    }

    public static List<Bank> allBank()
    {

        try(EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            EntityManagement.QBuilder<Bank> qBuilder = entityManagement.QueryBuilder();

            Root<Bank> root = qBuilder.criteriaQuery.from(Bank.class);

            qBuilder.criteriaQuery.select(root);

            return qBuilder.getList();

        }

    }

    public static List<Transaction> allTransaction()
    {

        try(EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            EntityManagement.QBuilder<Transaction> qBuilder = entityManagement.QueryBuilder();

            Root<Transaction> root = qBuilder.criteriaQuery.from(Transaction.class);

            qBuilder.criteriaQuery.select(root);

            return qBuilder.getList();

        }

    }

    public static List<String> allCard(Class<? extends Interface> deposit)
    {

        try(EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            EntityManagement.QBuilder<String> QBuilder = entityManagement.QueryBuilder();

            Root<? extends Interface> root = QBuilder.criteriaQuery.from(deposit);

            QBuilder.criteriaQuery.select(root.get("card"));

            return QBuilder.getList();

        }

    }

    public static List<String> allNumbers(Class<? extends Interface> deposit)
    {

        try(EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            EntityManagement.QBuilder<String> QBuilder = entityManagement.QueryBuilder();

            Root<? extends Interface> root = QBuilder.criteriaQuery.from(deposit);

            QBuilder.criteriaQuery.select(root.get("number"));

            return QBuilder.getList();

        }

    }

    public static List<String> allTid(Class<? extends Deposit> deposit)
    {

        Class<? extends Transaction> transactionClass;

        if(deposit == MelliDeposit.class)
        {

            transactionClass = MelliTransaction.class;

        }else
        if(deposit == SepahDeposit.class)
        {

            transactionClass = SepahTransaction.class;

        }else
        {

            return new ArrayList<>();

        }

        try(EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            EntityManagement.QBuilder<String> QBuilder = entityManagement.QueryBuilder();

            Root<? extends Transaction> root = QBuilder.criteriaQuery.from(transactionClass);

            QBuilder.criteriaQuery.select(root.get("tID"));

            return QBuilder.getList();

        }

    }

    public static Deposit singleCard(String card)
    {

        try(EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            EntityManagement.QBuilder<Deposit> qBuilder = entityManagement.QueryBuilder();

            Root<Deposit> root = qBuilder.criteriaQuery.from(Deposit.class);

            qBuilder.criteriaQuery.where(qBuilder.criteriaBuilder.equal(root.get("card"),card));

            if(qBuilder.count() == 1)
            {

                return qBuilder.getSingle();

            }

            return null;

        }

    }

}
