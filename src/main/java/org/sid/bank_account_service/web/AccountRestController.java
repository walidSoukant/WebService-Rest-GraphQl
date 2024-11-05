package org.sid.bank_account_service.web;

import org.sid.bank_account_service.dto.BankAccountRequestDTO;
import org.sid.bank_account_service.dto.BankAccountResponseDTO;
import org.sid.bank_account_service.entities.BankAccount;
import org.sid.bank_account_service.mappers.AccountMapper;
import org.sid.bank_account_service.repositories.BankAccountRepository;
import org.sid.bank_account_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountRestController {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountMapper accountMapper;

    @GetMapping("/bankAccounts")
    public List<BankAccount>bankAccounts(){
        return bankAccountRepository.findAll();
    }

    @GetMapping("/bankAccounts/{id}")
    public BankAccount bankAccounts(@PathVariable String id){
        return bankAccountRepository.findById(id).orElseThrow(()->new RuntimeException((String.format("Account %s not found",id))));
    }

    @PostMapping("/bankAccounts")
    public BankAccountResponseDTO save(@RequestBody BankAccountRequestDTO bankAccount)
    {
       return   accountService.addAccount(bankAccount);
    }

    @PutMapping("/bankAccounts/{id}")
    public BankAccount update(@PathVariable String id,@RequestBody BankAccount bankAccount)
    {
        BankAccount bankAccount1=bankAccountRepository.findById(id).orElseThrow();
        if(bankAccount.getBalance()!=null) bankAccount1.setBalance(bankAccount.getBalance());
        if(bankAccount.getCreatedAt()!=null) bankAccount1.setCreatedAt(new Date());
        if(bankAccount.getType()!=null) bankAccount1.setType(bankAccount.getType());
        if(bankAccount.getCurrency()!=null) bankAccount1.setCurrency(bankAccount.getCurrency());

        return bankAccountRepository.save(bankAccount1);
    }

    @DeleteMapping ("/bankAccounts/{id}")
    public void deleteAccount(@PathVariable String id){
        bankAccountRepository.deleteById(id);
    }

}
