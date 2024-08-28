package com.prototype.springP1.repository;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class DepositInfo implements Serializable
{

    private String card;

    private String number;

    private String cvv2;

    private Date expiry;

    private String password;

    public DepositInfo()
    {}

    public DepositInfo(String card, String number, String cvv2, String expiry, String password)
    {

        this.card = card;

        this.number = number;

        this.cvv2 = cvv2;

        if(Pattern.compile("^2[0-9]{3}-(0?[1-9]|1[0-2])-(0?[1-9]|[1-2][0-9]|3[0-1])$").matcher(expiry).matches())
        {

            this.expiry = Date.valueOf(expiry);

        }else
        {

            this.expiry = Date.valueOf(LocalDate.now().minusYears(1));

        }

        this.password = password;

    }

    public String getCard()
    {

        return card;

    }

    public void setCard(String card)
    {

        this.card = card;

    }

    public String getNumber()
    {

        return number;

    }

    public void setNumber(String number)
    {

        this.number = number;

    }

    public String getCvv2()
    {

        return cvv2;

    }

    public void setCvv2(String cvv2)
    {

        this.cvv2 = cvv2;

    }

    public String getPassword()
    {

        return password;

    }

    public void setPassword(String password)
    {

        this.password = password;

    }

    public Date getExpiry()
    {

        return expiry;

    }

    public void setExpiry(Date expiry)
    {

        this.expiry = expiry;

    }

}
