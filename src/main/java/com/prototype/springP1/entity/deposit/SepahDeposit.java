package com.prototype.springP1.entity.deposit;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Sepah")
public class SepahDeposit extends Deposit
{
}