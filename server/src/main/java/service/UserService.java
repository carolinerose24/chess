package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import model.requests.AuthRequest;
import model.requests.LoginRequest;
import model.requests.RegisterRequest;
import model.responses.UserResponse;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {


  private final AuthDAO authDAO;
  private final UserDAO userDAO;

  public UserService(AuthDAO authDAO, UserDAO userDAO){
    this.authDAO = authDAO;
    this.userDAO = userDAO;
  }


  public UserResponse register(RegisterRequest req) throws DataAccessException, BadRequestException, AlreadyTakenException {

    // if any fields are blank --> throw a bad request exception
    // use isBlank instead of isEmpty because "  " is not a valid field
    if(req.username() == null || req.username().isBlank() || req.password() == null || req.password().isBlank() || req.email() == null || req.email().isBlank()){
      throw new BadRequestException("Error: Empty Field");
    }
      if(userDAO.getUser(req.username()) != null) throw new AlreadyTakenException("Error: Username Already Exists");
      String hashPassword = createHash(req.password());
      userDAO.createUser(new UserData(req.username(), hashPassword, req.email()));
      String authToken = authDAO.createAndInsertAuthToken(req.username());
      return new UserResponse(authToken, req.username());
  }

  public UserResponse login(LoginRequest req) throws DataAccessException, UnauthorizedException{
      UserData user = userDAO.getUser(req.username());
      if(user == null) {
        throw new UnauthorizedException("Error: Invalid Credentials"); // misspelled username
      }

       if(!checkPasswordHash(req.password(), user.password())) {
         throw new UnauthorizedException("Error: Invalid Credentials");
       }

      String authToken = authDAO.createAndInsertAuthToken(req.username()); // returns void
      return new UserResponse(authToken, req.username());
  }

  public void logout(AuthRequest req) throws DataAccessException, UnauthorizedException{
      // get then delete
      AuthData authData = authDAO.getAuthToken(req.authToken());
      if(authData != null) {authDAO.deleteAuthToken(req.authToken());}
      else {throw new UnauthorizedException("Error: Bad Auth Token");}
  }


  // create a private helper method to verify the password and create a new hash

   private String createHash(String password){
     return BCrypt.hashpw(password, BCrypt.gensalt());
   }

   private boolean checkPasswordHash(String reqPassword, String passHash){
    return  BCrypt.checkpw(reqPassword, passHash);
   }

}
