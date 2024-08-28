package com.prototype.springP1.entity.transaction;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Melli")
public class MelliTransaction extends Transaction
{
}
