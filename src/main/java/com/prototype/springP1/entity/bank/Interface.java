package com.prototype.springP1.entity.bank;

import com.prototype.springP1.entity.deposit.Deposit;

import java.util.List;

public interface Interface
{

    void setDeposit(List<Deposit> deposit);

    List<Deposit> getDeposit();

    String getName();

}
