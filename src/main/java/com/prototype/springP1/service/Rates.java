package com.prototype.springP1.service;

import com.prototype.springP1.Application;
import com.prototype.springP1.entity.deposit.MelliDeposit;
import com.prototype.springP1.entity.deposit.SepahDeposit;
import com.prototype.springP1.entity.transaction.Builder;
import com.prototype.springP1.entity.transaction.Directory;
import com.prototype.springP1.repository.EntityManagement;
import jakarta.persistence.criteria.Root;

import java.util.List;

public abstract class Rates
{

    private Rates(){}

    public static void apply()
    {

        try (EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            List<MelliDeposit> depositList;

            EntityManagement.QBuilder<MelliDeposit> QBuilder = entityManagement.QueryBuilder();

            Root<MelliDeposit> root = QBuilder.criteriaQuery.from(MelliDeposit.class);

            QBuilder.criteriaQuery.select(root);

            depositList = QBuilder.getList();

            Directory transactionD = new Directory();

            for (MelliDeposit deposit : depositList)
            {

                Builder transactionBM = new Builder(MelliDeposit.class);

                long amount = deposit.getBalance() * deposit.getInterest()/100;

                deposit.setBalance(deposit.getBalance() + amount);

                transactionD.make(transactionBM, null, deposit, amount);

                entityManagement.merge(deposit);

                entityManagement.persist(transactionBM.getResult());

            }

            EntityManagement.QBuilder<SepahDeposit> sQBuilder = entityManagement.QueryBuilder();

            Root<SepahDeposit> sRoot = sQBuilder.criteriaQuery.from(SepahDeposit.class);

            sQBuilder.criteriaQuery.select(sRoot);

            List<SepahDeposit> sDepositList = sQBuilder.getList();

            for (SepahDeposit deposit : sDepositList)
            {

                Builder transactionBS = new Builder(SepahDeposit.class);

                long amount = deposit.getBalance() * deposit.getInterest()/100;

                deposit.setBalance(deposit.getBalance() + amount);

                transactionD.make(transactionBS, null, deposit, amount);

                entityManagement.merge(deposit);

                entityManagement.persist(transactionBS.getResult());

            }

        }

    }

}
