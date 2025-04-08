package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    
    private MessageDAO dao;

    public MessageService(){
        this.dao = new MessageDAO();
    }

    public Message createMessage(Message message){
        if(message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255){
            return null;
        }
        
        return dao.createMessage(message);
    }

    public List<Message> getAllMessages(){
        return dao.getAllMessages();
    }

    public Message getMessageByID(int messageID){
        return dao.getMessageById(messageID);
    }

    public Message deleteMessage(int messageID){
        Message m = dao.getMessageById(messageID);
        dao.deleteMessage(messageID);
        return m;
    }

    public Message updateMessage(int messageID, String text){
        if(text.length() == 0 || text.length() > 255){
            return null;
        }
        
        if (dao.updateMessage(messageID, text)){
            return dao.getMessageById(messageID);
        }
        return null;
    }

    public List<Message> getAllMessagesByUser(int userId){
        return dao.getAllUserMessage(userId);
    }
    
}
