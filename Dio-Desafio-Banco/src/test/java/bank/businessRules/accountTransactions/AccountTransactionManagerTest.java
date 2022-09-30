package bank.businessRules.accountTransactions;

import bank.account.Account;
import bank.account.AccountTransation.AbstractAccountTransaction;
import bank.account.AccountTransation.AccountTransaction;
import bank.account.AccountTransation.repository.TransactionRepository;
import bank.account.factory.AccountFactory;
import bank.account.factory.AccountFactoryImpl;
import bank.account.repository.AccountRepository;
import bank.account.repository.AccountRepositoryImpl;
import org.junit.jupiter.api.*;
import person.AbstractPerson;
import person.PhysicalPersonFactory;
import person.repository.PhysicalPersonRepository;
import util.validator.CpfValidator;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.function.Supplier;

class AccountTransactionManagerTest
{
    static Exception globalError;
    static AbstractPerson personA;
    static AbstractPerson personB;
    static PhysicalPersonRepository physicalPersonRepository;
    static PhysicalPersonFactory physicalPersonFactory;
    static Account accountA;
    static Account accountB;
    static AccountRepository accountRepository;
    static AccountFactory accountFactory;
    static AccountTransactionManager transactionManager;
    static TransactionRepository transactionRepository;

    static private <T, E> void waitFor(Supplier<T> supplier, Supplier<E> supplierErr)
    {
        try
        {
            while(supplier.get() == null && supplierErr.get() == null)
                Thread.sleep(10);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @BeforeAll
    static void setUp()
    {
        physicalPersonRepository = new PhysicalPersonRepository();
        physicalPersonFactory = new PhysicalPersonFactory(physicalPersonRepository, new CpfValidator());

        globalError = null;
        physicalPersonFactory.create("PessoaA", LocalDate.of(1980,5,23), "76539293932")
                .subscribe(
                    person -> {
                        personA = person;
                        System.out.println("A" + person);
                    },
                    error -> {
                        globalError = error;
                        System.out.println(error.getMessage());
                    }
                );
        waitFor(()->personA, ()->globalError);
        Assertions.assertNotNull(personA);

        globalError = null;
        physicalPersonFactory.create("PessoaB", LocalDate.of(1970,3,3), "4348921374")
            .subscribe(
                    person -> {
                        personB = person;
                        System.out.println("B" + person);
                    },
                    error->{
                        globalError = error;
                        System.out.println(error.getMessage());
                    });
        waitFor(()->personB, ()->globalError);
        Assertions.assertNotNull(personB);

        accountRepository = new AccountRepositoryImpl();
        accountFactory = new AccountFactoryImpl(accountRepository);

        globalError = null;
        accountA = accountFactory.createAccount("SimpleAccount",personA);
        waitFor(()->accountA, ()->globalError);

        globalError = null;
        accountB = accountFactory.createAccount("SavingAccount",personB);
        waitFor(()->accountB, ()->globalError);

        accountA.deposit(100);
        accountB.deposit(200);

        transactionRepository = new TransactionRepository();
        transactionManager = new AccountTransactionManager(accountRepository,transactionRepository);
    }

    @Test
    @Order(1)
    void deposit()
    {
        accountA.deposit(100);
        accountB.deposit(200);
   }

    @Test
    @Order(2)
    void transaction()
    {
        final AbstractAccountTransaction[] transaction = {null};
        final String[] errorMsg = {""};
        final float balance = accountA.getBalance();

        globalError = null;
        transactionManager.doWithdraw(accountA, 50)
            .subscribe(
                transact -> transaction[0] = transact,
                error -> errorMsg[0] = error.getMessage()
            );
        waitFor(()->transaction[0], ()->globalError);

        Assertions.assertNotNull(transaction[0]);
        Assertions.assertEquals("", errorMsg[0]);

        Assertions.assertTrue((balance-50)-accountA.getBalance() < 0.0001f);
    }

    @Test
    @Order(3)
    void transference()
    {
        final AccountTransaction[] transaction = {null};
        final String[] errorMsg = {""};
        final float balanceA = accountA.getBalance();
        final float balanceB = accountB.getBalance();

        globalError = null;
        transactionManager.doTransference(accountB, accountA, 50)
                .subscribe(
                        transact -> transaction[0] = transact,
                        error ->
                        {
                            System.out.println(error.getMessage());
                            errorMsg[0] = error.getMessage();
                        }
                );
        waitFor(()->transaction[0], ()->globalError);

        Assertions.assertNotNull(transaction[0]);
        Assertions.assertEquals("", errorMsg[0]);

        Assertions.assertTrue(Math.abs((balanceB-50)-accountB.getBalance()) < 0.0001f);
        Assertions.assertTrue(Math.abs((balanceA+50)- accountA.getBalance()) < 0.0001f);
    }

    @Test
    @Order(4)
    void doAccountMovement()
    {
        Predicate<AbstractAccountTransaction> predicate = transaction-> transaction.getAccountId() == accountA.getId();
        transactionRepository
        .filterSingleAccountTransactions(predicate)
        .subscribe(
            list ->{
                System.out.println("Account: "+accountA.getId());
                String format = "      %s %s %.2f";
                list.forEach(t ->
                {
                    String line = String.format(format,
                            t.getDateTime(), t.getOperationType(), t.getValue());
                    System.out.println(line);
                });
            },
            System.out::println
        );
    }

    @AfterAll
    static void tearDown()
    {
    }
}