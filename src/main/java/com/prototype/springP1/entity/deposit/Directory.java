package com.prototype.springP1.entity.deposit;

public class Directory
{

    public void make(BuilderInterface builder)
    {

        builder.generateCard();

        builder.generateNumber();

        builder.generateCvv2();

        builder.generateExpiry();

        builder.generateBalance();

        builder.generateSecondPassword();

        builder.generatePassword();

    }

}