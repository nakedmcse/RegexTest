import java.util.Random;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class regextest {
    public static String[] inputs = new String[100000];
    public static String[] outputsRegex = new String[100000];
    public static String[] outputsSplit = new String[100000];

    //Compiled regexes
    private static final Pattern PATTERN_END = Pattern.compile("&option=one$");
    private static final Pattern PATTERN_START_MIDDLE = Pattern.compile("option=one&");

    // Utility function to generate a random string of specified length
    public static String generateRandomText(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        Random rand = new Random();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(characters.length());
            result.append(characters.charAt(index));
        }

        return result.toString();
    }

    // Generate options string with options=one at a random placement
    public static String generateOptionsString() {
        List<String> options = new ArrayList<>(10);
        
        options.add("option=one");

        for (int i = 1; i < 10; i++) {
            String randomText = generateRandomText(5); // Generating a 5-letter random text.
            options.add(randomText + "=100");
        }

        // Shuffle the list to randomize the position of "option=one"
        Collections.shuffle(options);

        // Joining the shuffled options using "&" separator
        return String.join("&", options);
    }

    //Fill test data
    public static void FillInputs() {
        for (int i=0; i<100000; i++) {
            inputs[i]=generateOptionsString();
        }
    }

    //Remove options=one using split/join
    public static void TestSplitJoin() {
        for (int i=0; i<100000; i++) {
            String[] parts = inputs[i].split("&");
            List<String> partsList = new ArrayList<>(Arrays.asList(parts));
            partsList.removeIf(part -> part.contains("option"));
            outputsSplit[i] = String.join("&",partsList);
        }
    }

    //Remove options=one using regex
    public static void TestRegex() {
        for (int i=0; i<100000; i++) {
            outputsRegex[i]=inputs[i].replaceFirst("option=one$","").replaceFirst("option=one&","");
        }
    }

    //Remove options=one using compiled regex
    public static void TestCompiledRegex() {
        for (int i = 0; i < 100000; i++) {
            Matcher matcherEnd = PATTERN_END.matcher(inputs[i]);
            String intermediateResult = matcherEnd.replaceFirst("");
    
            Matcher matcherStartMiddle = PATTERN_START_MIDDLE.matcher(intermediateResult);
            outputsRegex[i] = matcherStartMiddle.replaceFirst("");
        }
    }

    public static void main(String[] args) {
        //Build test inputs
        System.out.println("Building Test Inputs");
        FillInputs();
        System.out.println();

        //Test split/join
        System.out.println("Testing Split/Join method");
        long startSplitJoinTime = System.currentTimeMillis();
        TestSplitJoin();
        long endSplitJoinTime = System.currentTimeMillis();
        System.out.println("Execution time " + (endSplitJoinTime - startSplitJoinTime) + " ms");
        System.out.println();

        //Test regex
        System.out.println("Testing Regex method");
        long startRegexTime = System.currentTimeMillis();
        TestRegex();
        long endRegexTime = System.currentTimeMillis();
        System.out.println("Execution time " + (endRegexTime - startRegexTime) + " ms");
        System.out.println();

        //Test compiled regex
        System.out.println("Testing Compiled Regex method");
        long startCompiledRegexTime = System.currentTimeMillis();
        TestCompiledRegex();
        long endCompiledRegexTime = System.currentTimeMillis();
        System.out.println("Execution time " + (endCompiledRegexTime - startCompiledRegexTime) + " ms");
        System.out.println();
    }
}