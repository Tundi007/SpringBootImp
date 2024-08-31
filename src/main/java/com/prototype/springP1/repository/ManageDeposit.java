package com.prototype.springP1.repository;

import com.prototype.springP1.Application;
import com.prototype.springP1.entity.bank.Bank;
import com.prototype.springP1.entity.deposit.*;
import com.prototype.springP1.entity.transaction.Transaction;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Scanner;

public class ManageDeposit
{

    public static Deposit validate(DepositInfo depositInfo)
    {

        try(EntityManagement entityManagement = Application.context.getBean(EntityManagement.class)) {

            if ((depositInfo.getCard() == null && depositInfo.getNumber() == null) ||
                    depositInfo.getCvv2() == null ||
                    depositInfo.getExpiry() == null ||
                    depositInfo.getPassword() == null) {

                return null;

            }

            EntityManagement.QBuilder<Deposit> qBuilder = entityManagement.QueryBuilder();

            Root<Deposit> root = qBuilder.criteriaQuery.from(Deposit.class);

            Deposit deposit = null;

            qBuilder.criteriaQuery.select(root);

            Predicate[] predicate = new Predicate[4];

            if (depositInfo.getCard() != null) {

                predicate[0] = qBuilder.criteriaBuilder.equal(root.get("card"), depositInfo.getCard());

            } else if (depositInfo.getNumber() != null) {

                predicate[0] = qBuilder.criteriaBuilder.equal(root.get("number"), depositInfo.getNumber());

            }

            predicate[1] = qBuilder.criteriaBuilder.equal(root.get("cvv2"), depositInfo.getCvv2());

            predicate[2] = qBuilder.criteriaBuilder.equal(root.get("expiry"), depositInfo.getExpiry());

            predicate[3] = qBuilder.criteriaBuilder.equal(root.get("password"), depositInfo.getPassword());

            qBuilder.criteriaQuery.where(predicate);

            qBuilder.criteriaQuery.distinct(true);

            if (qBuilder.count() == 1) {

                deposit = qBuilder.getSingle();

            }

            return deposit;

        }

    }

    public static void reset()
    {

        try(EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            for (Deposit deposit : EntitySelect.allDeposit())
            {

                entityManagement.remove(deposit);

            }

        }

        try(EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            for (Bank bank : EntitySelect.allBank())
            {

                entityManagement.remove(bank);

            }

        }

        try(EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            for (Transaction transaction : EntitySelect.allTransaction())
            {

                entityManagement.remove(transaction);

            }

        }

        try (EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            com.prototype.springP1.entity.bank.Melli m = new com.prototype.springP1.entity.bank.Melli();

            com.prototype.springP1.entity.bank.Sepah s = new com.prototype.springP1.entity.bank.Sepah();

            Directory d = new Directory();

            Builder bm1 = new Builder(MelliDeposit.class,1);

            Builder bm2 = new Builder(MelliDeposit.class,2);

            Builder bs1 = new Builder(SepahDeposit.class,1);

            Builder bs2 = new Builder(SepahDeposit.class,2);

            d.make(bs1);

            d.make(bs2);

            d.make(bm1);

            d.make(bm2);

            entityManagement.persist(s);

            entityManagement.persist(m);

            entityManagement.persist(bs1.getResult());

            entityManagement.persist(bs2.getResult());

            entityManagement.persist(bm1.getResult());

            entityManagement.persist(bm2.getResult());

        }

    }

    /*public static Deposit newDeposit()
    {

        try (EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            Builder builder;

            System.out.println("Select Your Bank: 1)Melli 2)Sepah");

            Directory directory = new Directory();

            switch (new Scanner(System.in).nextLine().trim())
            {
                case "1":
                {

                        System.out.println("Select A Plan: 1)Short Term 2)Long Term");

                        switch (new Scanner(System.in).nextLine().trim())
                        {

                            case "1":
                            {

                                builder = new Builder(MelliDeposit.class,1);

                            }break;

                            case "2":
                            {

                                builder = new Builder(MelliDeposit.class,2);

                            }break;

                            default:
                                return null;

                        }

                }break;

                case "2":
                {


                    System.out.println("Select A Plan: 1)Vault 2)Good Will");

                    switch (new Scanner(System.in).nextLine().trim())
                    {
                        case "1":
                        {

                            builder = new Builder(SepahDeposit.class,1);

                        }break;

                        case "2":
                        {

                            builder = new Builder(SepahDeposit.class,2);

                        }break;

                        default:
                            return null;

                    }

                }break;

                default:
                    return null;

            }

            directory.make(builder);

            entityManagement.persist(builder.getResult());

            return (Deposit)builder.getResult();

        }

    }*/

}
