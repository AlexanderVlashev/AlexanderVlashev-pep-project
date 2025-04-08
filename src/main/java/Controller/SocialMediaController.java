package Controller;

import java.util.List;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("register", this::createAccountHandler);
        app.post("login", this::loginHandler);
        app.post("messages", this::createMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);
        app.patch("messages/{message_id}", this::updateMessageHandler);
        app.get("accounts/{account_id}/messages", this::getAllUserMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void createAccountHandler(Context context){
        Account potentialAccount = context.bodyAsClass(Account.class);

        Account newAccount = accountService.createAccount(potentialAccount);

        if(newAccount == null){
            context.status(400);
        } else {
            context.json(newAccount);
            context.status(200);
        }
    }

    private void loginHandler(Context context){
        Account potentialAccount = context.bodyAsClass(Account.class);

        Account checkedAccount = accountService.logIn(potentialAccount);
        if(checkedAccount == null){
            context.status(401);
        } else {
            context.json(checkedAccount);
            context.status(200);
        }
        
    }

    private void createMessageHandler(Context context){
        Message potentialMessage = context.bodyAsClass(Message.class);

        Message checkedMessage = messageService.createMessage(potentialMessage);
        if(checkedMessage == null){
            context.status(400);
        } else {
            context.json(potentialMessage);
            context.status(200);
        }

    }
    private void getAllMessagesHandler(Context context){
        List<Message> messages = messageService.getAllMessages();
        
        context.json(messages);
        context.status(200);
    }

    private void getMessageByIdHandler(Context context){
       
        int id = Integer.valueOf(context.pathParam("message_id"));
        
        Message message = messageService.getMessageByID(id);
        if(message != null){
            context.json(message);
        }

        context.status(200);
    }

    private void deleteMessageHandler(Context context){
        int id = Integer.valueOf(context.pathParam("message_id"));

        Message message = messageService.deleteMessage(id);
        if(message != null){
            context.json(message);
        }

        context.status(200);
        
    }

    private void updateMessageHandler(Context context){
        int id = Integer.valueOf(context.pathParam("message_id"));
        String JsonString = context.body();
        int split = JsonString.indexOf(":");
        

        Message message = messageService.updateMessage(id, JsonString.substring(split+3, JsonString.length()-3));
        
        if(message == null){
            context.status(400);
        } else {
            context.json(message);
            context.status(200);
        }

    }

    private void getAllUserMessageHandler(Context context){
        int id = Integer.valueOf(context.pathParam("account_id"));

        List<Message> messages = messageService.getAllMessagesByUser(id);
        if(messages != null){
            context.json(messages);
        }

        context.status(200);
        
    }

}