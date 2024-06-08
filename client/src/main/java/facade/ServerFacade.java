package facade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.requests.LoginRequest;
import model.requests.RegisterRequest;
import model.responses.UserResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {

  private final String urlString;
  private ClientCommunicator com;

  public ServerFacade(String url){
    urlString = url;
    com = new ClientCommunicator();
  }

  public UserResponse registerUser(RegisterRequest req){
    try{
      return com.postRegister(urlString, req);
    } catch(Exception e){

//      e.printStackTrace();

//      System.out.println("Threw an exception, returning null");
      return null;
      // set the response to null??? it if is null, then it didn't work??????
    }
  }

  public UserResponse loginUser(LoginRequest req){

    return null;
  }







}
