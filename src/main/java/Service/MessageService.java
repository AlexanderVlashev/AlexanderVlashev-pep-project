package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    
    private MessageDAO dao;

    public MessageService(){
        this.dao = new MessageDAO();
    }

     /*
     * Creates a message for the service. 
     * @param message the message that we are trying to create
     * @returns the message that was create with it's id. Null if something went wrong or the
     * message is blank or the message is greater than 255 characters.
     */
    public Message createMessage(Message message){
        if(message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255){
            return null;
        }
        
        return dao.createMessage(message);
    }

     /*
     * Gets all messages in the service
     * @returns a list of messages.
     */
    public List<Message> getAllMessages(){
        return dao.getAllMessages();
    }

    /*
     * Finds the message that has the given message_id
     * @param messageID the message ID of the message we are trying to find
     * @returns The message with the corresponding messageID. Null if something went wrong.
     */
    public Message getMessageByID(int messageID){
        return dao.getMessageById(messageID);
    }

    /*
     * Deletes the message with the given messageID
     * @param messageID the message ID of the message we are trying to delete
     * @returns The deleted message. Null if something went wrong or if the message was already deleted.
     */
    public Message deleteMessage(int messageID){
        Message m = dao.getMessageById(messageID);
        dao.deleteMessage(messageID);
        return m;
    }

    /*
     * Updates the message with new message text.
     * @param messageID the message ID of the message we are trying to update
     * @param text the text that we are going to update with.
     * @returns The updated message. Null if something went wrong or the
     * message is blank or the message is greater than 255 characters.
     */
    public Message updateMessage(int messageID, String text){
        if(text.length() == 0 || text.length() > 255){
            return null;
        }
        
        if (dao.updateMessage(messageID, text)){
            return dao.getMessageById(messageID);
        }
        return null;
    }

    /*
     * Gets all message created by a specific user.
     * @param userID the userID of the user whose messages we are trying to find
     * @returns a list of messages by the user.
     */
    public List<Message> getAllMessagesByUser(int userId){
        return dao.getAllUserMessage(userId);
    }
    
}
