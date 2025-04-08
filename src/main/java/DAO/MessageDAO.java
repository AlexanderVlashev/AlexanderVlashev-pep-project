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
    
    public Message createMessage(Message message){
        try(Connection conn = ConnectionUtil.getConnection()){
            
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_enoch) VALUES(?,?,?)";
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

    public Message getMessageById(int messageId){
        try (Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement s = conn.prepareStatement(sql);
            s.setInt(0, messageId);

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
