package cn.cellcom.czt.wechat;

/**
 * Created by guan on 15-9-25.
 */
public class LoginParam {
    private String account;
    private String password;
    private String accountType;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
