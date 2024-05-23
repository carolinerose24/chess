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


  public AuthData register(RegisterRequest req) throws ChessGeneralException {

    try{
      if(userDAO.getUser(req.username()) != null) throw new AlreadyTakenException("User Already Exists");
      userDAO.createUser(new UserData(req.username(), req.password(), req.email())); // returns void
      String authToken = authDAO.createAndInsertAuthToken(req.username()); // returns void
      return new AuthData(authToken, req.username());
    } catch (DataAccessException e) {
      throw new ChessGeneralException(e);
    }
  }



  public AuthData login(LoginRequest req) throws ChessGeneralException{

    try{
      UserData user = userDAO.getUser(req.username());
      if(user == null) throw new BadRequestException("This user doesn't exist");
      if(!req.password().equals(user.password())) throw new UnauthorizedException("Wrong password");
      String authToken = authDAO.createAndInsertAuthToken(req.username()); // returns void
      return new AuthData(authToken, req.username());
    } catch (DataAccessException e){
      throw new ChessGeneralException(e);
    }
  }



  public void logout(AuthRequest req) throws ChessGeneralException{

    try{
      // get then delete
      AuthData authData = authDAO.getAuthToken(req.authToken());
      if(authData != null) {authDAO.deleteAuthToken(req.authToken());}
      else {
        // if it IS null, meaning this is an invalid Auth Token
        throw new UnauthorizedException("Bad Auth Token");
      }
    } catch (DataAccessException e){
      throw new ChessGeneralException(e);
    }

  }

}
