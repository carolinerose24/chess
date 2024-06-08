package facade;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.requests.*;
import model.responses.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class ClientCommunicator {

  // post, put, get, delete

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
//    catch(IOException e){
//      e.printStackTrace();
//    }
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
      return gson.fromJson(response.toString(), UserResponse.class);
    }
    else {
      // SERVER RETURNED AN HTTP ERROR - maybe change this??? IDK
      InputStream responseBody = connection.getErrorStream();
      throw new Exception(responseBody.toString());
    }
  }



  public UserResponse postLogin(String urlString, LoginRequest req) throws Exception{
    URI uri = new URI(urlString + "/session");
    HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);

    // no request headers for register, just a body
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("username", req.username());
    jsonObject.addProperty("password", req.password());

    String requestBodyString = jsonObject.toString();
    try (OutputStream os = connection.getOutputStream();
         PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, "UTF-8"))) {
      writer.print(requestBodyString);
      writer.flush();
    }
//    catch(IOException e){
//      e.printStackTrace();
//    }
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
      return gson.fromJson(response.toString(), UserResponse.class);
    }
    else {
      // SERVER RETURNED AN HTTP ERROR - maybe change this??? IDK
      InputStream responseBody = connection.getErrorStream();
      throw new Exception(responseBody.toString());
    }
  }
















































  public void doPost(String urlString) throws IOException {
    URL url = new URL(urlString);

    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    connection.setReadTimeout(5000);
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);

    // Set HTTP request headers, if necessary
    // connection.addRequestProperty("Accept", "text/html");

    connection.connect();

    try(OutputStream requestBody = connection.getOutputStream();) {
      // Write request body to OutputStream ...
    }

    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
      // Get HTTP response headers, if necessary
      // Map<String, List<String>> headers = connection.getHeaderFields();

      // OR

      //connection.getHeaderField("Content-Length");

      InputStream responseBody = connection.getInputStream();
      // Read response body from InputStream ...
    }
    else {
      // SERVER RETURNED AN HTTP ERROR

      InputStream responseBody = connection.getErrorStream();
      // Read and process error response body from InputStream ...
    }
  }

  public void doGet(String urlString) throws IOException {
    URL url = new URL(urlString);

    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    connection.setReadTimeout(5000);
    connection.setRequestMethod("GET");

    // Set HTTP request headers, if necessary
    // connection.addRequestProperty("Accept", "text/html");
    // connection.addRequestProperty("Authorization", "fjaklc8sdfjklakl");

    connection.connect();

    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
      // Get HTTP response headers, if necessary
      // Map<String, List<String>> headers = connection.getHeaderFields();

      // OR

      //connection.getHeaderField("Content-Length");

      InputStream responseBody = connection.getInputStream();
      // Read and process response body from InputStream ...
    } else {
      // SERVER RETURNED AN HTTP ERROR

      InputStream responseBody = connection.getErrorStream();
      // Read and process error response body from InputStream ...
    }
  }

  public void doDelete(String urlString) throws IOException{


  }

  public void doPut(String urlString) throws IOException{

  }


}
