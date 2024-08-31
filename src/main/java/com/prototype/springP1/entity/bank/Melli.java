package com.prototype.springP1.entity.bank;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Melli")
public class Melli extends Bank
{
}