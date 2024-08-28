package com.prototype.springP1.entity.transaction;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Sepah")
public class SepahTransaction extends Transaction
{
}