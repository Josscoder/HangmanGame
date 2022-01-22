package words;

import java.util.Arrays;

public enum WordsType {
  RANDOM,
  ADULTS,
  FUNNY,
  GOOD,
  IMPOSSIBLE,
  KIDS;

  public static boolean contains(String id) {
    return (
      Arrays
        .stream(WordsType.values())
        .filter(wordsType -> wordsType.name().toLowerCase().equalsIgnoreCase(id)
        )
        .findFirst()
        .orElse(null) !=
      null
    );
  }
}
