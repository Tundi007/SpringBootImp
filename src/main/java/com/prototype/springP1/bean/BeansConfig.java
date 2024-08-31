package com.prototype.springP1.bean;

import com.prototype.springP1.entity.bank.Bank;
import com.prototype.springP1.entity.deposit.Deposit;
import com.prototype.springP1.entity.transaction.Transaction;
import com.prototype.springP1.repository.DepositInfo;
import com.prototype.springP1.repository.EntityManagement;
import jakarta.persistence.PersistenceContext;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Transactional
@PersistenceContext
public class BeansConfig
{

    private static final SessionFactory sessionFactory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();

    @Bean
    @Scope(scopeName = "prototype")
    public Transaction newTransaction()
    {

        return new Transaction();

    }

    @Bean
    @Scope(scopeName = "prototype")
    public Deposit newDeposit()
    {

        return new Deposit();

    }

    @Bean
    @Scope(scopeName = "prototype")
    public Bank newBank()
    {

        return new Bank();

    }

    @Bean
    @Scope(scopeName = "prototype")
    public DepositInfo newDepositInfo()
    {

        return new DepositInfo();

    }

    @Bean
    @Scope(scopeName = "prototype")
    public EntityManagement newEntityManagement()
    {

        return new EntityManagement();

    }

}
