package com.prototype.springP1.service;

import com.prototype.springP1.Application;
import com.prototype.springP1.entity.deposit.*;
import com.prototype.springP1.repository.EntityManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceDeposit
{

    @GetMapping("/atm/new-deposit")
    private String newDeposit(@RequestParam String plan)
    {

        String result = "";

        try (EntityManagement entityManagement = Application.context.getBean(EntityManagement.class))
        {

            Builder bDeposit;

            Directory d = new Directory();

            switch (plan)
            {
                case "melli1" -> {

                    bDeposit = new Builder(MelliDeposit.class, 1);

                    plan = "Short Term";
                }
                case "melli2" -> {

                    bDeposit = new Builder(MelliDeposit.class, 2);

                    plan = "Long Term";
                }
                case "sepah1" -> {

                    bDeposit = new Builder(SepahDeposit.class, 1);

                    plan = "Vault";
                }
                case "sepah2" ->
                {

                    bDeposit = new Builder(SepahDeposit.class, 2);

                    plan = "Good Will";

                }
                case null, default ->
                {

                    return "Please Select An Option";

                }
            }

            d.make(bDeposit);

            Interface deposit = bDeposit.getResult();

            entityManagement.persist(deposit);

            result += "\nPlan: " + plan;

            result += "\nCard: " + deposit.getCard();

            result += "\nNumber: " + deposit.getNumber();

            result += "\nExpiry: " + deposit.getExpiry();

            result += "\nCvv2: " + deposit.getCvv2();

            result += "\nBalance: " + deposit.getBalance();

            result += "\nInterest: " + deposit.getInterest();

            result += "\nPassword: " + deposit.getPassword();

        }

        return result;

    }

    @GetMapping("/atm/rates")
    private String applyRates()
    {

        Rates.apply();

        return "Success";

    }

}