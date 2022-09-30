package bank.account.AccountTransation;

import java.time.LocalDateTime;

public interface AccountTransaction
{
    enum OperationType {
        D('D'),
        C('C');
        public final char label;

        OperationType(char label)
        {
            this.label = label;
        }
    }
    OperationType getOperationType();
    Long getId();
    LocalDateTime getDateTime();
    String getAccountId();

    float getValue();
}
