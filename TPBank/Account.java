package tpbankk;
/**
 *
 * @author phamt
 */
public class Account {
    private String accountNumber;
    private String password;

    public Account() {
    }

    public Account(String accountNumber, String password) {
        this.accountNumber = accountNumber;
        this.password = password;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPassword() {
        return password;
    }
}
