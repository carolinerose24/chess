package model.responses;

import model.GameData;

import java.util.Collection;

public record ListGamesResponse(Collection<GameData> game) {
  // I think this needs to be named as game, not games??
}
