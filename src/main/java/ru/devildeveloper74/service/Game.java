package ru.devildeveloper74.service;

import ru.devildeveloper74.model.GameResult;

import java.util.concurrent.ExecutionException;

public interface Game {
    GameResult play(int betAmount) throws ExecutionException, InterruptedException;

}
