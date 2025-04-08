package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
    /*
     * Connects to the database and inserts an message into the database
     * @param message the message that we are trying to insert
     * @returns the message that was inserted with it's id or null if something went wrong
     */
    public Message createMessage(Message message){
        try(Connection conn = ConnectionUtil.getConnection()){
            
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?,?,?)";
            PreparedStatement request = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            request.setInt(1, message.getPosted_by());
            request.setString(2, message.getMessage_text());
            request.setLong(3, message.getTime_posted_epoch());

            int checkinsert = request.executeUpdate();
            
            if(checkinsert == 0 ){
                throw new SQLException("Unable to insert into Table");
            }

            ResultSet keys = request.getGeneratedKeys();

            if(keys.next()){
                message.setMessage_id(keys.getInt(1));
            }

            return message;

        } catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

        /*
     * Connects to the database and returns all messages in the database
     * @returns a list of  messages or null if something went wrong
     */
    public List<Message> getAllMessages(){
        try(Connection conn = ConnectionUtil.getConnection()){

            String sql = "SELECT * FROM message";
            PreparedStatement s = conn.prepareStatement(sql);

            List<Message> messages = new ArrayList<>();

            ResultSet result = s.executeQuery();

            while(result.next()){
                Message m = new Message();
                m.setMessage_id(result.getInt("message_id"));
                m.setPosted_by(result.getInt("posted_by"));
                m.setMessage_text(result.getString("message_text"));
                m.setTime_posted_epoch(result.getLong("time_posted_epoch"));
                messages.add(m);
            }

            return messages;

        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Connects to the database and tries to find a message based on it's messageID
     * @param message the messageID of the message it is trying to find
     * @returns the message that it was searching for or null if something went wrong
     */
    public Message getMessageById(int messageId){
        try (Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement s = conn.prepareStatement(sql);
            s.setInt(1, messageId);

            ResultSet result = s.executeQuery();

            if(result.next()){
                Message m = new Message();
                m.setMessage_id(result.getInt("message_id"));
                m.setPosted_by(result.getInt("posted_by"));
                m.setMessage_text(result.getString("message_text"));
                m.setTime_posted_epoch(result.getLong("time_posted_epoch"));
                return m;
            } 


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Connects to the database and deletes a message into the database
     * @param messageid the messageid of the message that we are trying to delete
     */
    public void deleteMessage(int messageId){
        try (Connection conn = ConnectionUtil.getConnection()){
            
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement s = conn.prepareStatement(sql);
            s.setInt(0, messageId);
            
            s.executeUpdate();

        } catch (SQLException e) {
           e.printStackTrace();
        }

    }

    /*
     * Connects to the database and updates a message's text
     * @param messageID the message_id of the message whose text we are updatingt
     * @param text the new text that is replacing the old text of the message.
     * @returns a boolean for if the message was successfuly updated or not
     */
    public boolean updateMessage(int messageID, String text){
        try (Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement s = conn.prepareStatement(sql);
            
            s.setString(1, text);
            s.setInt(2, messageID);

            int checkUpdate = s.executeUpdate();
            
            if(checkUpdate == 0){
                return false;
            }

            return true;

        } catch (SQLException e) {
           e.printStackTrace();
        }

        return false;
    }
    /*
     * Connects to the database and gets a list of all messages from a user
     * @param userID the id of the user whose message we are trying to find
     * @returns a list of all messages from the given user or null if something went wrong
     */
    public List<Message> getAllUserMessage(int userId){
        try (Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement s = conn.prepareStatement(sql);
            s.setInt(1, userId);

            List<Message> messages = new ArrayList<>();

            ResultSet result = s.executeQuery();

            while(result.next()){
                Message m = new Message();
                m.setMessage_id(result.getInt("message_id"));
                m.setPosted_by(result.getInt("posted_by"));
                m.setMessage_text(result.getString("message_text"));
                m.setTime_posted_epoch(result.getLong("time_posted_epoch"));
                messages.add(m);
            }

            return messages;

        } catch (SQLException e) {
           e.printStackTrace();
        }

        return null;
    }
}
