package ru.devildeveloper74;

import ru.devildeveloper74.config.GameConfig;
import ru.devildeveloper74.model.GameResult;
import ru.devildeveloper74.service.Game;
import ru.devildeveloper74.service.MatrixGenerator;
import ru.devildeveloper74.service.RewardCalculator;
import ru.devildeveloper74.service.MatrixStatisticCollector;
import ru.devildeveloper74.service.impl.ScratchGame;
import ru.devildeveloper74.service.impl.matrix.MatrixStatisticCollectorImpl;
import ru.devildeveloper74.service.impl.matrix.RandomMatrixGenerator;
import ru.devildeveloper74.service.impl.rewards.DefaultRewardCalculator;
import ru.devildeveloper74.util.GameConfigLoader;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        if (args.length != 2) {
//            System.out.println("Usage: java -jar <your-jar-file> --config <config.json> --betting-amount <amount>");
//            return;
//        }
//
//        String configFilePath = args[1];
//        int betAmount = Integer.parseInt(args[3]);

        try {
            GameConfig config = GameConfigLoader.loadFromFile("/home/night/IdeaProjects/CyberSpeedGame/src/main/resources/defaultConfig.json");
            MatrixGenerator matrixGenerator = new RandomMatrixGenerator(config);
            RewardCalculator rewardCalculator = new DefaultRewardCalculator(config);

            Game game = new ScratchGame(matrixGenerator, rewardCalculator);


            GameResult result = game.play(100);

//            ResponseWriter writer = new ConsoleResponseWriter();
//            writer.writeResponse(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}