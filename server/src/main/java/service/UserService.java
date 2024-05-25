package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import model.requests.AuthRequest;
import model.requests.LoginRequest;
import model.requests.RegisterRequest;

public class UserService {


  private final AuthDAO authDAO;
  private final UserDAO userDAO;

  public UserService(AuthDAO authDAO, UserDAO userDAO){
    this.authDAO = authDAO;
    this.userDAO = userDAO;
  }


  public AuthData register(RegisterRequest req) throws DataAccessException, BadRequestException, AlreadyTakenException {

    // if any fields are blank --> throw a bad request exception i think???
    if(req.username() == null || req.username().isBlank() || req.password() == null || req.password().isBlank() || req.email() == null || req.email().isBlank()){
      throw new BadRequestException("Error: Empty Field");
      // use isBlank instead of isEmpty because "  " is not a valid field
    }

      if(userDAO.getUser(req.username()) != null) throw new AlreadyTakenException("Error: User Already Exists");

      userDAO.createUser(new UserData(req.username(), req.password(), req.email())); // returns void
      String authToken = authDAO.createAndInsertAuthToken(req.username()); // returns void
      return new AuthData(authToken, req.username());
  }

  public AuthData login(LoginRequest req) throws DataAccessException, UnauthorizedException{
      UserData user = userDAO.getUser(req.username());
      if(user == null) throw new UnauthorizedException("Error: Invalid Credentials"); // misspelled username
      if(!req.password().equals(user.password())) throw new UnauthorizedException("Error: Invalid Credentials");
      String authToken = authDAO.createAndInsertAuthToken(req.username()); // returns void
      return new AuthData(authToken, req.username());
  }

  public void logout(AuthRequest req) throws DataAccessException, UnauthorizedException{

      // get then delete
      AuthData authData = authDAO.getAuthToken(req.authToken());
      if(authData != null) {authDAO.deleteAuthToken(req.authToken());}
      else {
        // if it IS null, meaning this is an invalid Auth Token
        throw new UnauthorizedException("Bad Auth Token");
      }

  }

}
