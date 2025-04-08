package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    /*
     * Connects to the database and inserts an account into the database
     * @param account the account that we are trying to insert
     * @returns the account that was inserted with it's id or null if something went wrong
     */
    public Account createAccount(Account account){
        try(Connection conn = ConnectionUtil.getConnection()){

            String sql2 = "INSERT INTO account(username, password) VALUES(?,?)";
            PreparedStatement s2 = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);

            s2.setString(1, account.getUsername());
            s2.setString(2, account.getPassword());

            int checkinsert = s2.executeUpdate();
            
            if(checkinsert == 0 ){
                throw new SQLException("Unable to insert into Table");
            }

            ResultSet keys = s2.getGeneratedKeys();

            if(keys.next()){
                account.setAccount_id(keys.getInt(1));
            }
            conn.setAutoCommit(true);
            
            return account;

        } catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    /*
     * Connects to the database and checks if the account is in the database
     * @param account the account that we are trying to check
     * @returns the account that was checked with it's id or null if something went wrong
     */
    public Account logIn(Account account){
        try(Connection conn = ConnectionUtil.getConnection()){
            
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement s = conn.prepareStatement(sql);
            s.setString(1, account.getUsername());
            s.setString(2, account.getPassword());

            ResultSet result = s.executeQuery();

            if(result.next()){
                account.setAccount_id(result.getInt("account_id"));
                return account;
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
