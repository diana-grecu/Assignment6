import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
	private ArrayList playersNames = new ArrayList();
	private int[] places = new int[6];
	private int[] purses = new int[6];
	private boolean[] inPenaltyBox = new boolean[6];

	private List<Question> popQuestions = new LinkedList<>();
	private List<Question> scienceQuestions = new LinkedList<>();
	private List<Question> sportsQuestions = new LinkedList<>();
	private List<Question> rockQuestions = new LinkedList<>();

	private int currentPlayerIndex = 0;
	private boolean isGettingOutOfPenaltyBox;
	private boolean answerStatus;

	public Game() {
		for (int i = 0; i < 50; i++) {
			addQuestion("Pop", i);
			addQuestion("Science", i);
			addQuestion("Sports", i);
			addQuestion("Rock", i);
		}
	}

	private void addQuestion(String category, int index) {
		switch (category) {
			case "Pop":
				popQuestions.add(new Question(category, index));
				break;
			case "Science":
				scienceQuestions.add(new Question(category, index));
				break;
			case "Sports":
				sportsQuestions.add(new Question(category, index));
				break;
			case "Rock":
				rockQuestions.add(new Question(category, index));
				break;
		}
	}

	public boolean isPlayable() {
		return (getNumPlayers() >= 2);
	}

	public boolean addPlayer(String playerName) {

		playersNames.add(playerName);
		places[getNumPlayers()] = 0;
		purses[getNumPlayers()] = 0;
		inPenaltyBox[getNumPlayers()] = false;

		System.out.println(playerName + " was added");
		System.out.println("They are player number " + playersNames.size());
		return true;
	}

	public int getNumPlayers() {
		return playersNames.size();
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public int getPlayerLocation(int playerIndex) {
		return places[playerIndex];
	}

	public void diceRoll(int roll) {
		System.out.println(playersNames.get(currentPlayerIndex) + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (inPenaltyBox[currentPlayerIndex]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;

				System.out.println(playersNames.get(currentPlayerIndex) + " is getting out of the penalty box");
				places[currentPlayerIndex] = places[currentPlayerIndex] + roll;
				if (places[currentPlayerIndex] > 11)
					places[currentPlayerIndex] = places[currentPlayerIndex] - 12;

				System.out.println(playersNames.get(currentPlayerIndex)
						+ "'s new location is "
						+ places[currentPlayerIndex]);
				System.out.println("The category is " + getCurrentCategory());
				askQuestion();
			} else {
				System.out.println(playersNames.get(currentPlayerIndex) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}

		} else {

			places[currentPlayerIndex] = places[currentPlayerIndex] + roll;
			if (places[currentPlayerIndex] > 11)
				places[currentPlayerIndex] = places[currentPlayerIndex] - 12;

			System.out.println(playersNames.get(currentPlayerIndex)
					+ "'s new location is "
					+ places[currentPlayerIndex]);
			System.out.println("The category is " + getCurrentCategory());
			askQuestion();
		}

	}

	private void askQuestion() {
		String currentCategory = getCurrentCategory();
		String question = getNextQuestion(currentCategory);
		System.out.println(question);
	}

	public void setAnswerStatus(boolean status) {
		this.answerStatus = status;
	}

	public String getCurrentCategory() {
		if (places[currentPlayerIndex] % 4 == 0)
			return "Pop";
		else if (places[currentPlayerIndex] % 4 == 1)
			return "Science";
		else if (places[currentPlayerIndex] % 4 == 2)
			return "Sports";
		else
			return "Rock";
	}

	public String getNextQuestion(String category) {
		List<Question> questions;
		switch (category) {
			case "Pop":
				questions = popQuestions;
				break;
			case "Science":
				questions = scienceQuestions;
				break;
			case "Sports":
				questions = sportsQuestions;
				break;
			case "Rock":
				questions = rockQuestions;
				break;
			default:
				return "";
		}
		Question question = questions.remove(0);
		questions.add(question);
		return question.getCategory() + " Question " + question.getNumber();
	}


	public boolean isCorrectAnswer() {
			if (inPenaltyBox[currentPlayerIndex]) {
				if (isGettingOutOfPenaltyBox) {
					System.out.println("Answer was correct!!!!");
					purses[currentPlayerIndex]++;
					System.out.println(playersNames.get(currentPlayerIndex)
							+ " now has "
							+ purses[currentPlayerIndex]
							+ " Gold Coins.");

					boolean winner = didPlayerWin();
					currentPlayerIndex++;
					if (currentPlayerIndex == playersNames.size())
						currentPlayerIndex = 0;

					return winner;
				} else {
					currentPlayerIndex++;
					if (currentPlayerIndex == playersNames.size())
						currentPlayerIndex = 0;
					return true;
				}

			} else {

				System.out.println("Answer was corrent!!!!");
				purses[currentPlayerIndex]++;
				System.out.println(playersNames.get(currentPlayerIndex)
						+ " now has "
						+ purses[currentPlayerIndex]
						+ " Gold Coins.");

				boolean winner = didPlayerWin();
				currentPlayerIndex++;
				if (currentPlayerIndex == playersNames.size())
					currentPlayerIndex = 0;

				return winner;
			}
		}


	private void updateCurrentPlayerIndex() {
		currentPlayerIndex++;
		if (currentPlayerIndex == playersNames.size())
			currentPlayerIndex = 0;
	}

	public boolean handleWrongAnswer() {
		System.out.println("Question was incorrectly answered");
		System.out.println(playersNames.get(currentPlayerIndex) + " was sent to the penalty box");
		inPenaltyBox[currentPlayerIndex] = true;

		currentPlayerIndex++;
		if (currentPlayerIndex == playersNames.size())
			currentPlayerIndex = 0;
		return true;
	}

	private boolean didPlayerWin() {
		return !(purses[currentPlayerIndex] == 6);
	}
}
