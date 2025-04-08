package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    
    private AccountDAO dao;

    public AccountService(){
        this.dao = new AccountDAO();
    }

    /*
     * Creates an account for the service. 
     * @param account the account that we are trying to create
     * @returns the account that was create with it's id. Null if something went wrong or the
     * account's username is blank and password is less then 4 characters.
     */
    public Account createAccount(Account account){
        
        if(account.getPassword().length() < 4 || account.getUsername().length() == 0){
            return null;
        }
        
        return dao.createAccount(account);
    }

     /*
     * Verifies an account for the service. 
     * @param account the account that we are trying to verify
     * @returns the account that was verified with it's id. Null if something went wrong.
     */
    public Account logIn (Account account){
        return dao.logIn(account);
    }
}
