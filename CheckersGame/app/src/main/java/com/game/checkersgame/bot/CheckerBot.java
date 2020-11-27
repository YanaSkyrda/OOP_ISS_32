package com.game.checkersgame.bot;

import com.game.checkersgame.models.Coordinates;
import com.game.checkersgame.models.Figure;
import com.game.checkersgame.models.Figures;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class CheckerBot {
    private static Random random = new Random();

    public static Coordinates chooseChecker(Figures checkers, String botColor, long delay) {
        waitFor(delay);
        Map<Figure, List<Coordinates>> available = checkers.getAvailableListByColor(botColor);
        Coordinates res = null;
        for (Map.Entry<Figure, List<Coordinates>> entry : available.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                res = checkers.find(entry.getKey());
                break;
            }
        }
        return res;
    }

    public static Coordinates chooseMove(Figures checkers, Figure checker, long delay) {
        waitFor(delay);
        List<Coordinates> available = checkers.buildAvailable(checker);
        if (available.isEmpty())
            return null;
        return available.get(random.nextInt(available.size()));
    }

    private static void waitFor(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
