package com.prototype.springP1.entity.deposit;

import com.prototype.springP1.repository.EntitySelect;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Builder implements BuilderInterface
{

    public Builder(Class<? extends Deposit> dClass, int option)
    {

        this.dClass = dClass;

        if(this.dClass == MelliDeposit.class)
        {

            deposit = new MelliDeposit();

            if(option == 1)/*web server and container*/
            {

                deposit.setInterest(8);

                startingCard = "4891110";

            }
            else
            if(option ==2)
            {

                deposit.setInterest(10);

                startingCard = "48910200";

            }else
            {

                startingCard = "";

            }

        }
        else
        if(this.dClass == SepahDeposit.class)
        {

            deposit = new SepahDeposit();

            if(option == 1)
            {

                deposit.setInterest(15);

                startingCard = "6219018";

            }
            else
            if(option ==2)
            {

                deposit.setInterest(20);

                startingCard = "6219200";

            }else
            {

                startingCard = "";

            }

        }else
        {

            startingCard = "";

        }

    }

    private final Class<? extends Deposit> dClass;

    private final String startingCard;

    private Deposit deposit;

    @Override
    public void generateBalance()
    {

        deposit.setBalance(50000);

    }

    @Override
    public void generateCvv2()
    {

        int length = 4;

        if(new Random().nextInt(2) == 1)
            length = 3;


        StringBuilder cvv2 = new StringBuilder();

        for(int i=0;i<length;i++)
            cvv2.append(new Random().nextInt(10));

        deposit.setCvv2(cvv2.toString());

    }

    @Override
    public void generateExpiry()
    {

        deposit.setExpiry(Date.valueOf(LocalDate.now().plusYears(5).plusMonths(1).withDayOfMonth(1)));

    }

    @Override
    public void setExpiry()
    {

        LocalDate expiry = new Date(0).toLocalDate();

        String userInput = "A";

        Pattern year = Pattern.compile("^[0-9]{1,4}$");

        Pattern month = Pattern.compile("^[1-9]|1[0-2]$");

        while(!year.matcher(userInput).matches())
        {

            System.out.println("Enter Expiry Year");

            userInput = new Scanner(System.in).nextLine();

        }

        expiry = expiry.withYear(Integer.parseInt(userInput));

        userInput = "A";

        while(!month.matcher(userInput).matches())
        {

            System.out.println("Enter Expiry Month");

            userInput = new Scanner(System.in).nextLine();

        }

        expiry = expiry.withMonth(Integer.parseInt(userInput));

        deposit.setExpiry(Date.valueOf(expiry));

    }

    @Override
    public void generateCard()
    {

        StringBuilder card;

        do
        {

            card = new StringBuilder();

            card.insert(0, startingCard);

            while (card.toString().length() < 16)
            {

                card.append(new Random().nextInt(10));

            }

        }while(EntitySelect.allCard(dClass).contains(card.toString()));

        deposit.setCard(card.toString());

    }

    @Override
    public void generateNumber()
    {

        StringBuilder number;

        do
        {

            number = new StringBuilder();

            number.insert(0, "");

            while(number.toString().length() < 10)
            {

                number.append(new Random().nextInt(10));

            }

        }while(EntitySelect.allNumbers(dClass).contains(number.toString()));

        deposit.setNumber(number.toString());

    }

    @Override
    public void generatePassword()
    {

        deposit.setPassword(""+
            new Random().nextInt(10) +
            new Random().nextInt(10) +
            new Random().nextInt(10) +
            new Random().nextInt(10));

    }

    @Override
    public void generateSecondPassword()
    {

        deposit.setSecond_password("" +
            new Random().nextInt(10) +
            new Random().nextInt(10) +
            new Random().nextInt(10) +
            new Random().nextInt(10) +
            new Random().nextInt(10) +
            new Random().nextInt(10));

    }

    @Override
    public void setCvv2()
    {

        String cvv2 = new Scanner(System.in).nextLine();

        deposit.setCvv2(cvv2);

    }

    @Override
    public void setPassword()
    {

        Pattern password = Pattern.compile("^[0-9]{4}$");

        String userInput;

        System.out.println("Set A 4 Digit Password For Your New Deposit");

        while(!password.matcher(userInput = new Scanner(System.in).nextLine().trim()).matches())
        {

            System.out.println("Only 4 Digit Password Is Accepted:");

        }

        deposit.setPassword(userInput);

    }

    @Override
    public void setSecondPassword()
    {

        Pattern password = Pattern.compile("^[0-9]{4,8}$");

        String userInput;

        System.out.println("Set A 2nd Password Between 4 And 8 Digits For Your New Deposit");

        while(!password.matcher(userInput = new Scanner(System.in).nextLine().trim()).matches())
        {

            System.out.println("2nd Password Must Be Between 4 And 8 Digits:");

        }

        deposit.setSecond_password(userInput);

    }

    @Override
    public void setNumber()
    {

        System.out.println("Enter Deposit Number");

        String userInput = new Scanner(System.in).nextLine();

        deposit.setNumber(userInput);

    }

    @Override
    public void setCard()
    {

        System.out.println("Enter Card Number");

        String userInput = new Scanner(System.in).nextLine();

        deposit.setCard(userInput);

    }

    public Interface getResult()
    {

        return deposit;

    }

}