package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    
    private AccountDAO dao;

    public AccountService(){
        this.dao = new AccountDAO();
    }

    public Account createAccount(Account account){
        
        if(account.getPassword().length() < 4){
            return null;
        }
        
        return dao.createAccount(account);
    }

    public Account logIn (Account account){
        return dao.logIn(account);
    }
}
