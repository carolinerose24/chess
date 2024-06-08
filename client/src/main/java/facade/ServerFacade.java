package facade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.requests.AuthRequest;
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

  //      e.printStackTrace();

  public UserResponse registerUser(RegisterRequest req){
    try{
      return com.postRegister(urlString, req);
    } catch(Exception e){return null;}
  }

  public UserResponse loginUser(LoginRequest req){
    try{
      return com.postLogin(urlString, req);
    } catch(Exception e){return null;}
  }

//  public UserResponse logoutUser(AuthRequest req){
//    try{
//      return com.deleteLogout(urlString, req);
//    } catch(Exception e){return null;}
//  }









}
