import words.WordsType;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class HangmanGame {

  private static Scanner input;

  public static void main(String[] args) {
    hangman();
  }

  public static List<String> generateRandomWords(
    String type,
    int numberOfWords
  ) {
    String fileName =
        Paths.get(".").normalize().toAbsolutePath() + "\\words\\" +
      type +
      ".txt";
    File wordList = new File(fileName);
    List<String> words = new ArrayList<>();
    Scanner reader = null;

    try {
      reader = new Scanner(wordList);
    } catch (FileNotFoundException e) {
      System.out.println("file \"" + fileName + "\" not found");
      System.exit(0);
    }

    while (reader.hasNextLine()) {
      String word = reader.nextLine();
      words.add(word);
    }

    Collections.shuffle(words);

    return words.stream().limit(numberOfWords).collect(Collectors.toList());
  }

  public static void hangman() {
    input = new Scanner(System.in);

    System.out.println(
      "    Welcome to HANGMAN GAME v1.0 by Jossscoder    " + "\n"
    );

    boolean isWordsTypeSelected = false;
    String wordsType = WordsType.RANDOM.name().toLowerCase();

    while (!isWordsTypeSelected) {
      System.out.println("    Please select a words type    ");

      Arrays
        .stream(WordsType.values())
        .forEach(type ->
          System.out.println("    - " + type.name().toLowerCase())
        );

      System.out.println("\nOption Selected:");

      String newWordsType = input.nextLine().toLowerCase();

      if (newWordsType.isEmpty()) {
        newWordsType = WordsType.RANDOM.name().toLowerCase();
      }

      if (!WordsType.contains(newWordsType)) {
        return;
      }

      isWordsTypeSelected = true;

      wordsType = newWordsType;

      System.out.println("YOU SELECTED the type of words: " + newWordsType);
    }

    List<String> words = generateRandomWords(wordsType, 10);

    Random random = new Random();
    String word = (words.get(random.nextInt(words.size()))).toUpperCase();

    String wordReplacedByChar = word.replaceAll("[A-Z]", "_ ");

    System.out.println("Let's play the game");
    startGame(word, wordReplacedByChar);
  }

  public static void startGame(String word, String wordReplacedByChar) {
    int wrong = 0;

    String guess;

    StringBuilder guesses = new StringBuilder();

    char letter;

    boolean guessesContainsGuess;
    boolean guessInWord;

    while (wrong < 6 && wordReplacedByChar.contains("_")) {
      System.out.println(wordReplacedByChar + "\n");

      int temp = 6 - wrong;

      if (wrong != 0) {
        System.out.println("You have " + temp + " guesses left.");
      }

      System.out.print("Your Guess:");

      guess = input.nextLine().toUpperCase();

      if (guess.isEmpty()) {
        guess = "ñ";
      }

      letter = guess.charAt(0);

      guessesContainsGuess = (guesses.toString().indexOf(letter)) != -1;

      guesses.append(letter);

      letter = Character.toUpperCase(letter);
      System.out.println();

      if (guessesContainsGuess) {
        System.out.println("You ALREADY guessed '" + letter + "'. \n");
      }

      guessInWord = (word.indexOf(letter)) != -1;

      if (guessInWord) {
        System.out.println("'" + letter + "' is present in the word.");
        System.out.print("\n");

        for (int position = 0; position < word.length(); position++) {
          if (
            word.charAt(position) == letter &&
            wordReplacedByChar.charAt(position) != letter
          ) {
            wordReplacedByChar = wordReplacedByChar.replaceAll("_ ", "_");
            wordReplacedByChar =
              wordReplacedByChar.substring(0, position) +
              letter +
              wordReplacedByChar.substring(position + 1).replaceAll("_", "_ ");
          }
        }
      } else {
        System.out.println("'" + letter + "' is not present in the word.");
        wrong++;

        printHangman(wrong);
      }
    }

    if (wrong == 6) {
      System.out.println("Maximum limit of incorrect guesses reached.");
    } else {
      System.out.print(
        "The word is: '" + wordReplacedByChar + "'. Well Played, You did it!"
      );
    }
  }

  public static void printHangman(int wrong) {
    switch (wrong) {
      case 1:
        System.out.println(
          "                       ___\n" +
          "                      |   |\n" +
          "                      O   |\n" +
          "                          |\n" +
          "                          |\n" +
          "                        __|"
        );
        break;
      case 2:
        System.out.println(
          "                       ___\n" +
          "                      |   |\n" +
          "                      O/  |\n" +
          "                          |\n" +
          "                          |\n" +
          "                        __|"
        );
        break;
      case 3:
        System.out.println(
          "                       ___\n" +
          "                      |   |\n" +
          "                     _O/  |\n" +
          "                          |\n" +
          "                          |\n" +
          "                        __|"
        );
        break;
      case 4:
        System.out.println(
          "                       ___\n" +
          "                      |   |\n" +
          "                     _O/  |\n" +
          "                      |   |\n" +
          "                          |\n" +
          "                        __|"
        );
        break;
      case 5:
        System.out.println(
          "                       ___\n" +
          "                      |   |\n" +
          "                     _O/  |\n" +
          "                      |   |\n" +
          "                       \\  |\n" +
          "                        __|"
        );
        break;
      case 6:
        System.out.println(
          "                       ___\n" +
          "                      |   |\n" +
          "     YOU LOST!       _O/  |\n" +
          "                      |   |\n" +
          "                     / \\  |\n" +
          "                        __|"
        );
        break;
    }
  }
}
