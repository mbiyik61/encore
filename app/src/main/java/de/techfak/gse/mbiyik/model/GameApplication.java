package de.techfak.gse.mbiyik.model;

import android.app.Application;

import de.techfak.gse.mbiyik.model.Game;

public class GameApplication extends Application {
    private Game game;

    public Game getGame() {
        return game; }
    public void setGame(Game game) {
        this.game = game; }

}
