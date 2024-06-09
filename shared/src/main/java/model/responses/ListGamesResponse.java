package model.responses;

import com.google.gson.annotations.SerializedName;
import model.GameData;

import java.util.Collection;

public record ListGamesResponse(@SerializedName("games")Collection<GameData> game) {
}
