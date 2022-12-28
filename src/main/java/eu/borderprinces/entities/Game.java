package eu.borderprinces.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Game {

    public HashMap<Integer, HashMap<Integer, Tile>> scenario;
    public List<Lair> monsterBuildings = new ArrayList<>();
    public List<Monster> monsters = new ArrayList<>();

    public Player player;

    public List<Building> playerBuildings = new ArrayList<>();

    public Game(HashMap<Integer, HashMap<Integer, Tile>> scenario){
        this.scenario = scenario;
    }


    public Optional<Tile> getTile(int row, int colum) {
        return Optional.of(scenario)
                .map(x -> x.get(row))
                .map(x -> x.get(colum));
    }
}
