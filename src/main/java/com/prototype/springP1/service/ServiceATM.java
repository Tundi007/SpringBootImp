package com.prototype.springP1.service;

import com.prototype.springP1.Application;
import com.prototype.springP1.entity.bank.Bank;
import com.prototype.springP1.entity.deposit.Deposit;
import com.prototype.springP1.entity.transaction.Transaction;
import com.prototype.springP1.repository.DepositInfo;
import com.prototype.springP1.repository.EntityManagement;
import com.prototype.springP1.repository.EntitySelect;
import com.prototype.springP1.repository.ManageDeposit;
import jakarta.persistence.criteria.Root;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
public class ServiceATM
{

    @GetMapping("/atm")
    private String atmOptions(@RequestParam String option)
    {

        switch (option)
        {

            case "add" ->
            {

                return "Success";

                /*return "Add Funds\n" +
                    addFunds(info, amount);*/

            }
            case "take" ->
            {

                return "Success";

                /*return "Take Funds\n" +
                    takeFunds(info, amount);*/

            }
            case "ctc" ->
            {

                return "Success";

                /*return "Card To Card\n" +
                    cardToCard(info, amount, destination);*/

            }
            case "cb" ->
            {

                return "Success";

                /*return "Check Balance" +
                    checkBalance(info);*/

            }
            case "ch" ->
            {

                return "Success";

                /*return "Transaction History" +
                    transactionHistory(info);*/

            }
            case null, default ->
            {

                return "Please Select An Option";

                /*return "Please Select An Option";*/

            }

        }

    }

    @PostMapping("/atm/card-info")
    public String getCard(@RequestParam String card)
    {

        try(EntityManagement em = Application.context.getBean(EntityManagement.class))
        {

            String result = "";

            EntityManagement.QBuilder<Bank> qBuilder = em.QueryBuilder();

            Root<Deposit> root = qBuilder.criteriaQuery.from(Deposit.class);

            qBuilder.criteriaQuery.where(qBuilder.criteriaBuilder.equal(root.get("card"), card));

            qBuilder.criteriaQuery.select(root.get("bank"));

            if(qBuilder.getSingle() != null)
            {

                result += "\nBank: " + qBuilder.getSingle().getName();

            }else
            {

                result += "\nBank: Not Recognized";

            }

            result += "\nCard: " + card;

            return result;

        }

    }

    @PostMapping("/atm/transaction-history")
    private String transactionHistory(
        @RequestParam String card,
        @RequestParam String cvv2,
        @RequestParam String date,
        @RequestParam String password)
    {

        DepositInfo info = new DepositInfo(card,null,cvv2,date,password);

        StringBuilder result = new StringBuilder();

        Deposit temp = ManageDeposit.validate(info);

        if(temp == null)
        {

            result.append("\nTransaction ID: N/A");

            result.append("\nDate").append(Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));

            result.append("\nAmount: N/A");

            result.append("\nCard: Incorrect Info");

            result.append("\nBalance: N/A");

            return result.toString();

        }

        try(EntityManagement em = Application.context.getBean(EntityManagement.class))
        {

            int counter = 1;

            Deposit deposit = em.find(temp.getID(), Deposit.class);

            result.append("\nSent:\n");

            for (Transaction sent : deposit.getSentTransaction())
            {

                result.append("\n").append(counter).append(":");

                counter++;

                result.append("\nTransaction ID: ").append(sent.getTID());

                result.append("\nDate: ").append(sent.getDate());

                result.append("\nAmount:").append(sent.getAmount());

                result.append("\nSender: ").append(sent.getSender().getCard());

                if(sent.getDestination()!=null)
                {

                    result.append("\nDestination: ").append(sent.getDestination().getCard());

                }

                result.append("\nBalance: ").append(sent.getSender_Balance()).append("\n");

            }

            counter = 1;

            result.append("\n----------------------------\nReceived:\n");

            for (Transaction got : deposit.getReceivedTransaction()) {

                result.append("\n").append(counter).append(":");

                counter++;

                result.append("\nTransaction ID: ").append(got.getTID());

                result.append("\nDate: ").append(got.getDate());

                result.append("\nAmount:").append(got.getAmount());

                if(got.getSender()!=null)
                {

                    result.append("\nSender: ").append(got.getSender().getCard());

                }

                result.append("\nDestination: ").append(got.getDestination().getCard());

                result.append("\nBalance: ").append(got.getDestination_Balance()).append("\n");

            }

            return result.toString();

        }

    }

    @PostMapping("/atm/add-funds")
    private String addFunds(
        @RequestParam String card,
        @RequestParam String cvv2,
        @RequestParam String date,
        @RequestParam String password,
        @RequestParam long amount)
    {

        DepositInfo info = new DepositInfo(card,null,cvv2,date,password);

        Deposit deposit = ManageDeposit.validate(info);

        if (deposit == null)
        {

            return receipt(null,-1);

        }

        Transaction transaction = MakeTransaction.addBalance(deposit,amount);

        return receipt(transaction,0);

    }

    @PostMapping("/atm/take-funds")
    private String takeFunds(
        @RequestParam String card,
        @RequestParam String cvv2,
        @RequestParam String date,
        @RequestParam String password,
        @RequestParam long amount)
    {

        DepositInfo info = new DepositInfo(card,null,cvv2,date,password);

        Deposit deposit = ManageDeposit.validate(info);

        if (deposit == null)
        {

            return receipt(null,-1);

        }

        Transaction transaction = MakeTransaction.takeBalance(deposit,amount);

        if(transaction == null)
        {

            return receipt(null,-1);

        }

        return receipt(transaction,1);

    }

    @PostMapping("/atm/card-to-card")
    private String cardToCard(
        @RequestParam String card,
        @RequestParam String cvv2,
        @RequestParam String date,
        @RequestParam String password,
        @RequestParam String destination,
        @RequestParam long amount)
    {

        DepositInfo info = new DepositInfo(card,null,cvv2,date,password);

        Deposit deposit = ManageDeposit.validate(info);

        Deposit to = EntitySelect.singleCard(destination);

        if (deposit == null || to == null)
        {

            return receipt(null,-1);

        }

        Transaction transaction = MakeTransaction.cardToCard(deposit, to, amount);

        if(transaction == null)
        {

            return receipt(null,-1);

        }

        return receipt(transaction,2);

    }

    @PostMapping("/atm/check-balance")
    private String checkBalance(
        @RequestParam String card,
        @RequestParam String cvv2,
        @RequestParam String date,
        @RequestParam String password)
    {

        DepositInfo info = new DepositInfo(card,null,cvv2,date,password);

        Deposit deposit = ManageDeposit.validate(info);

        if (deposit == null)
        {

            return receipt(null,-1);

        }

        return "Card: " + deposit.getCard() +
                "\nBalance: " + deposit.getBalance() +
                "\nDate:" + Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));

    }

    @PostMapping("/atm/receipt")
    private String receipt(Transaction transaction, int option)
    {

        if(option == -1)
        {

            return "Transaction ID: N/A" +
                    "\nDate: " + Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)) +
                    "\nAmount: N/A" +
                    "\nCard: Transaction Failed" +
                    "\nBalance: N/A";

        }else
        if(option == 0)
        {

            return "Transaction ID: " + transaction.getTID() +
                    "\nDate: " + transaction.getDate() +
                    "\nAmount: " + transaction.getAmount() +
                    "\nDestination: " + transaction.getDestination().getCard() +
                    "\nBalance: " + transaction.getDestination_Balance();

        }else
        if(option == 1)
        {

            return "Transaction ID: " + transaction.getTID() +
                    "\nDate: " + transaction.getDate() +
                    "\nAmount: " + transaction.getAmount() +
                    "\nSender: " + transaction.getSender().getCard() +
                    "\nBalance: " + transaction.getSender_Balance();

        }else
        if(option == 2)
        {

            return "Transaction ID: " + transaction.getTID() +
                    "\nDate: " + transaction.getDate() +
                    "\nAmount: " + transaction.getAmount() +
                    "\nSender: " + transaction.getSender().getCard() +
                    "\nDestination: " + transaction.getDestination().getCard() +
                    "\nBalance: " + transaction.getSender_Balance();

        }

        return "";

    }

}