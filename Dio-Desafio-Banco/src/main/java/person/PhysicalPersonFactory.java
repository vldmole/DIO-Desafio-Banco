package person;

import bank.account.AbstractAccount;
import bank.account.repository.AccountRepositoryImpl;
import person.repository.PhysicalPersonRepository;
import util.observable.Observable;
import util.validator.Validator;

import java.time.LocalDate;

public class PhysicalPersonFactory
{
    //----------------------------------------------------------------------------------------------------------
    //--to get access to protected methods----------------------------------------------------------------------
    class ObservableResult<T> extends Observable<T>
    {
        //-----------------------------------------------------------------------------------------
        @Override
        protected void fireNext(T data)
        {
            super.fireNext(data);
        }

        //-----------------------------------------------------------------------------------------
        @Override
        protected void fireError(Exception exception)
        {
            super.fireError(exception);
        }
    }

    //--------------------------------------------------------------------------------------------
    protected final Validator cpfValidator;
    protected final PhysicalPersonRepository physicalPersonRepository;

    public PhysicalPersonFactory(PhysicalPersonRepository physicalPersonRepository, Validator cpfValidator)
    {
        this.physicalPersonRepository = physicalPersonRepository;
        this.cpfValidator = cpfValidator;
    }

    public
    Observable<PhysicalPerson>
    create(String name, LocalDate birthDate, String cpf)
    {
        ObservableResult<PhysicalPerson> observable = new ObservableResult<>();

        if(!this.cpfValidator.isValid(cpf))
            throw new IllegalArgumentException(String.format("cpf is invalid '%s'", cpf));

        PhysicalPerson physicalPerson =  new PhysicalPerson(name,birthDate, cpf);
        this.physicalPersonRepository.add(physicalPerson)
            .subscribe(
                person -> {
                    observable.fireNext(person);
                },
                error ->{
                    observable.fireError(error);
                }
        );
        return observable;
    }
}
