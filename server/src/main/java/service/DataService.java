package service;

import model.AuthData;
import model.GameData;

public class DataService {


  public GameData listGames(AuthData authdata){
    // just GameData or a collection of game data???? not sure how to handle more than I record/row
    return null;
  }

  public GameData createGame(AuthData authData, GameData gameData){


    // not sure if gameName should be passed like that, but I think it is OK to pass a GameData object where just the
    // game name is initialized and everything else is null
    return null;
  }

  public void clear(AuthData authData){

  }

}
