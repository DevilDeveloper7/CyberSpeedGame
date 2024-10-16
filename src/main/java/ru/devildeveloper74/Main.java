package ru.devildeveloper74;

import ru.devildeveloper74.config.GameConfig;
import ru.devildeveloper74.model.GameResult;
import ru.devildeveloper74.service.Game;
import ru.devildeveloper74.service.MatrixGenerator;
import ru.devildeveloper74.service.ResponseWriter;
import ru.devildeveloper74.service.RewardCalculator;
import ru.devildeveloper74.service.impl.ConsoleResponseWriter;
import ru.devildeveloper74.service.impl.ScratchGame;
import ru.devildeveloper74.service.impl.matrix.RandomMatrixGenerator;
import ru.devildeveloper74.service.impl.rewards.DefaultRewardCalculator;
import ru.devildeveloper74.util.GameConfigLoader;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java -jar <your-jar-file> --config <config.json> --betting-amount <amount>");
            return;
        }

        String configFilePath = args[1];
        int betAmount = Integer.parseInt(args[3]);

        try {
            GameConfig config = GameConfigLoader.loadFromFile(configFilePath);
            MatrixGenerator matrixGenerator = new RandomMatrixGenerator(config);
            RewardCalculator rewardCalculator = new DefaultRewardCalculator(config);
            Game game = new ScratchGame(matrixGenerator, rewardCalculator);
            GameResult result = game.play(betAmount);
            ResponseWriter writer = new ConsoleResponseWriter();
            writer.writeResponse(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}