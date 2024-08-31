package com.prototype.springP1.entity.deposit;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Melli")
public class MelliDeposit extends Deposit implements Interface
{
}