package facade;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.requests.*;
import model.responses.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;


public class ClientCommunicator {

  public UserResponse postRegister(String urlString, RegisterRequest req) throws Exception{
    URI uri = new URI(urlString + "/user");
    HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);

    // no request headers for register, just a body
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("username", req.username());
    jsonObject.addProperty("password", req.password());
    jsonObject.addProperty("email", req.email());

    String requestBodyString = jsonObject.toString();
    try (OutputStream os = connection.getOutputStream();
         PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, "UTF-8"))) {
      writer.print(requestBodyString);
      writer.flush();
    }

    connection.connect();

    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
      // If there was a good response, we now need to read in the response body (which is json)
      StringBuilder response = new StringBuilder();
      try (InputStream responseBody = connection.getInputStream();
           BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody, StandardCharsets.UTF_8))) {
        // use a try with resources to auto close the input stream and the buffered reader
        String line;
        while ((line = reader.readLine()) != null) {
          response.append(line);
        }
      }

      Gson gson = new Gson();
      return gson.fromJson(response.toString(), UserResponse.class);
    } else if(connection.getResponseCode() == 400){
      // Bad Request Error
      throw new Exception("There was a bad request. Make sure there are no empty fields.");
    } else if(connection.getResponseCode() == 403){
      // Already Taken Error
      throw new Exception("That username is already taken.");
    } else if(connection.getResponseCode() == 500){
      // Data Access Exception
      throw new Exception("There was a problem with the server.");
    } else {
      // Shouldn't reach here ...
      throw new Exception("Unknown Error");
    }
  }

  public UserResponse postLogin(String urlString, LoginRequest req) throws Exception{

    URI uri = new URI(urlString + "/session");
    HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
    connection.setRequestMethod("POST");
    connection.setReadTimeout(5000);
    // need this for post again
    connection.setDoOutput(true);

    // no request headers for register, just a body
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("username", req.username());
    jsonObject.addProperty("password", req.password());

    String requestBodyString = jsonObject.toString();
    try (OutputStream os = connection.getOutputStream();
         PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(os, "UTF-8"))) {

      printWriter.print(requestBodyString);
      printWriter.flush();
    }

    connection.connect();

    if (connection.getResponseCode() == 200) {
      // if there is a good response
      StringBuilder response = new StringBuilder();
      try (InputStream responseBody = connection.getInputStream();
           BufferedReader buffReader = new BufferedReader(new InputStreamReader(responseBody))) {
        String eachline;
        while ((eachline = buffReader.readLine()) != null) {
          response.append(eachline);
          // reach in the whole response body with this try block
        }
      }
      Gson gson = new Gson();
      return gson.fromJson(response.toString(), UserResponse.class);
    } else if(connection.getResponseCode() == 401){
      throw new Exception("There are no users that match these credentials.");

    } else if(connection.getResponseCode() == 500){
      throw new Exception("There was a problem with the server.");

    } else {
      throw new Exception("Unknown Error"); // shouldn't ever get here i think...
    }
  }

  public boolean deleteLogout(String urlString, AuthRequest req) throws Exception{
    URI uri = new URI(urlString + "/session");
    HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("DELETE");

    connection.setRequestProperty("Authorization", req.authToken()); // Add Authorization header
    connection.setDoOutput(false); // No request body for DELETE
    connection.connect();

    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
      return true;
    } else if(connection.getResponseCode() == 401){
      throw new Exception("This was an unauthorized logout");

    } else if(connection.getResponseCode() == 500){
      throw new Exception("There was a problem with the server.");
    } else {
      throw new Exception("Unknown Error");
    }
  }

  public CreateGameResponse postCreateGame(String urlString, CreateGameRequest req) throws Exception{
    URI uri = new URI(urlString + "/game");
    HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Authorization", req.authToken());
    connection.setDoOutput(true);

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("gameName", req.gameName());
    String requestBodyString = jsonObject.toString();

    try (OutputStream os = connection.getOutputStream();
         PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, "UTF-8"))) {
      writer.print(requestBodyString);
      writer.flush();
    }
    connection.connect();


    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
      StringBuilder response = new StringBuilder();
      try (InputStream responseBody = connection.getInputStream();
           BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody, StandardCharsets.UTF_8))) {

        String line;
        while ((line = reader.readLine()) != null) {
          response.append(line);

        }
      }

      Gson gson = new Gson();
      return gson.fromJson(response.toString(), CreateGameResponse.class);
    } else if(connection.getResponseCode() == 400){
      throw new Exception("There was a bad request. Make sure there are no empty fields.");
    } else if(connection.getResponseCode() == 401){
      throw new Exception("This action was unauthorized.");
    } else if(connection.getResponseCode() == 500){
      throw new Exception("There was a problem with the server.");
    } else {
      throw new Exception("Unknown Error");
    }
  }

  public ListGamesResponse getListGames(String urlString, AuthRequest req) throws Exception{

    URI uri = new URI(urlString + "/game");
    HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
    connection.setReadTimeout(5000);

    connection.setRequestMethod("GET");
    connection.setRequestProperty("Authorization", req.authToken());
    connection.setDoOutput(false);  // I think i need this, not sure though
    connection.connect();

    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

      StringBuilder response = new StringBuilder();
      try (InputStream responseBody = connection.getInputStream();
           BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody, StandardCharsets.UTF_8))) {
        String line;
        while ((line = reader.readLine()) != null) {
          response.append(line);
        }

      }
      Gson gson = new Gson();
      return gson.fromJson(response.toString(), ListGamesResponse.class);
    } else if(connection.getResponseCode() == 401){
      throw new Exception("You are unauthorized to request this action.");

    } else if(connection.getResponseCode() == 500){
      throw new Exception("There was a problem with the server.");
    } else {
      throw new Exception("Unknown Error");

    }
  }

  public boolean putJoinGame(String urlString, JoinGameRequest req) throws Exception{

    URI uri = new URI(urlString + "/game");
    HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("PUT");

    connection.setRequestProperty("Authorization", req.authToken());
    connection.setDoOutput(true);


    String playerColor;
    if(req.playerColor().equalsIgnoreCase("white")){
      playerColor = "WHITE";
    } else if(req.playerColor().equalsIgnoreCase("black")){
      playerColor = "BLACK";
    } else {
      playerColor = "BAD REQUEST";
    }

    // IF IT IS AN EMPTY STRING, DOES THAT MEAN WE WANTED IT TO OBSERVE THE GAME??
    // I CAN JUST FIX THIS LATER THOUGH

    // no request headers for register, just a body
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("playerColor", playerColor);
    jsonObject.addProperty("gameID", req.gameID());
    String requestBodyString = jsonObject.toString();

    try (OutputStream os = connection.getOutputStream();
         PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, "UTF-8"))) {
      writer.print(requestBodyString);
      writer.flush();
    }
    connection.connect();


    // no response body, just check for errors
    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
      return true;
    } else if(connection.getResponseCode() == 400){
      throw new Exception("There was a bad request.");

    } else if(connection.getResponseCode() == 401){
      throw new Exception("You are unauthorized to request this action.");
    } else if(connection.getResponseCode() == 403){
      throw new Exception("This color is already taken.");
    } else if(connection.getResponseCode() == 500){
      throw new Exception("There was a problem with the server.");
    } else {
      throw new Exception("Unknown Error");
    }
  }

  public boolean deleteClear(String urlString) throws Exception{

    URI uri = new URI(urlString + "/db");
    HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("DELETE");
    connection.setDoOutput(false); // No request body for DELETE
    connection.connect();

    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
      return true;
    } else {
      throw new Exception("Unable to clear the database at this time.");
    }
  }
}
