package com.prototype.springP1.service;

import com.prototype.springP1.repository.ManageDeposit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Managing
{

    @GetMapping("/reset")
    public String reset()
    {

        ManageDeposit.reset();

        return "Success";

    }

    @GetMapping("/rates")
    public String rates()
    {

        Rates.apply();

        return "Success";

    }

}
