package baseball.controller;

import baseball.model.Game;
import baseball.view.InputView;
import baseball.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void runGame() {
        outputView.printGameStartMessage();

        boolean gameInProgress = true;
        while (gameInProgress) {
            Game game = new Game();

            gameInProgress = playGame(game);

            if (!gameInProgress) {
                gameInProgress = !promptForRestartOrExit();
                outputView.printGameEndMessage();
                outputView.printEndMessage();
            }
        }
        outputView.closeConsole();
    }

    private boolean playGame(Game game) {
        boolean gameInProgress = true;
        while (gameInProgress) {
            String userInput = inputView.getUserInput();
            List<Integer> userNumbers = parseUserInput(userInput);

            if (game.isCorrectGuess(userNumbers)) {
                gameInProgress = false;
            }
        }
        return gameInProgress;
    }

    private List<Integer> parseUserInput(String userInput) {
        List<Integer> userNumbers = parseInputNumbers(userInput);
        validateUserInput(userNumbers);
        return userNumbers;
    }

    private List<Integer> parseInputNumbers(String userInput) {
        List<Integer> userNumbers = new ArrayList<>();
        for (char digitChar : userInput.toCharArray()) {
            int digit = Character.getNumericValue(digitChar);
            userNumbers.add(digit);
        }
        return userNumbers;
    }

    private void validateUserInput(List<Integer> userNumbers) {
        if (userNumbers.size() != 3) {
            throw new IllegalArgumentException("3자리 숫자를 입력해야 합니다.");
        }

        if (!areNumbersInRange(userNumbers)) {
            throw new IllegalArgumentException("1에서 9 사이의 수만 입력 가능합니다.");
        }

        if (!areNumbersDistinct(userNumbers)) {
            throw new IllegalArgumentException("서로 다른 숫자를 입력해야 합니다.");
        }
    }

    private boolean areNumbersInRange(List<Integer> userNumbers) {
        for (int num : userNumbers) {
            if (num < 1 || num > 9) {
                return false;
            }
        }
        return true;
    }

    private boolean areNumbersDistinct(List<Integer> userNumbers) {
        for (int i = 0; i < userNumbers.size(); i++) {
            for (int j = i + 1; j < userNumbers.size(); j++) {
                if (userNumbers.get(i).equals(userNumbers.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean promptForRestartOrExit() {
        int choice = inputView.getRestartOrExitChoice();
        if (!(choice == 1 || choice == 2)) {
            throw new IllegalArgumentException("1 또는 2만 입력 가능합니다.");
        }
        return choice == 2;
    }
}
