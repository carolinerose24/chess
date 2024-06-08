package facade;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.requests.RegisterRequest;
import model.responses.UserResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class ClientCommunicator {

  // post, put, get, delete

  public UserResponse postRegister(String urlString, RegisterRequest req) throws Exception{
//    URL url = new URL(urlString);
//    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

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

//    System.out.println("Just made the body, it was: ");
//    System.out.println(jsonObject.toString());

    String requestBodyString = jsonObject.toString();
    try (OutputStream os = connection.getOutputStream();
         PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, "UTF-8"))) {
      writer.print(requestBodyString);
      writer.flush();
    }
//    catch(IOException e){
//      e.printStackTrace();
//    }

//    System.out.println("Set the response body successfully");


    connection.connect();


    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

      // Read the response body

//      try(InputStream responseBody = connection.getInputStream()){
//        System.out.println(responseBody.toString());
//      }


      StringBuilder response = new StringBuilder();
      try (InputStream responseBody = connection.getInputStream();
           BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody, StandardCharsets.UTF_8))) {

        String line;
        while ((line = reader.readLine()) != null) {
          response.append(line);
        }
      }

      // Print the raw response
//      System.out.println("Raw response: " + response.toString());
      Gson gson = new Gson();
      return gson.fromJson(response.toString(), UserResponse.class);
    }
    else {
      // SERVER RETURNED AN HTTP ERROR
      InputStream responseBody = connection.getErrorStream();
//      System.out.println("Returned an http error, didn't work");
//      System.out.println(responseBody.toString());
      throw new Exception(responseBody.toString());
      // Read and process error response body from InputStream ...
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
