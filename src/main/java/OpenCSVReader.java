import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenCSVReader {
    private static final String path = "src/resources/test!.csv";
    private static final String outpath = "src/resources/";

    public static void main(String[] args) throws IOException{
      List<String> firstThree = issolateFirstThreeArguements(path);
      List<String> halfFormatted =  commasIntoSemiColons(firstThree);
      List<String> fullyFormatted = formatQuotes(halfFormatted);
      saveTxtFile(fullyFormatted9   );
    }

    public static List<String> issolateFirstThreeArguements(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String currentLine = reader.readLine();
        List<String> translations = new ArrayList<>();

        String firstThreeArguementsInQuotes = "^\"(.*?)\",\"(.*?)\",\"(.*?)\"";
        Pattern reg = Pattern.compile(firstThreeArguementsInQuotes);
        while(currentLine != null){
            Matcher m = reg.matcher(currentLine);
            if(m.find()){
                translations.add(m.group(0));
            }
            currentLine = reader.readLine();
        }

        return translations;
    }
    
    public static List<String> commasIntoSemiColons(List<String> preFormated){
        String commasBetweenTwoQuotes = "(?<=\")(,)(?=\")";
        String replace = ";";
        Pattern reg = Pattern.compile(commasBetweenTwoQuotes);

        List<String> output = new ArrayList<>();
        for(int i = 0; i < preFormated.size(); i++){
            String line = preFormated.get(i);
            Matcher m = reg.matcher(line);
            String translation = m.replaceAll(replace);
            output.add(translation);
        }
        return output;
    }

    public static List<String> formatQuotes(List<String> halfFormatted){
        List<String> deletedQuotes = deleteLeadingQuotes(halfFormatted);
        List<String> formattedQuotes = laggingQuotesToSpaces(deletedQuotes);
        return formattedQuotes;
    }
    public static List<String> laggingQuotesToSpaces(List<String> halfFormatted){
        String allQuotes = "(\")(?=;)|(\")$";
        String replace = " ";
        Pattern reg = Pattern.compile(allQuotes);

        List<String> output = new ArrayList<>();
        for(int i = 0; i < halfFormatted.size(); i++){
            String line = halfFormatted.get(i);
            Matcher m = reg.matcher(line);
            String translation = m.replaceAll(replace);
            output.add(translation);
        }
        return output;
    }

    public static List<String> deleteLeadingQuotes(List<String> halfFormatted){
        String leadingQuote = "^(\")|(?<=;)(\")";
        String replace = "";
        Pattern reg = Pattern.compile(leadingQuote);

        List<String> output = new ArrayList<>();
        for(int i = 0; i < halfFormatted.size(); i++){
            String line = halfFormatted.get(i);
            Matcher m = reg.matcher(line);
            String translation = m.replaceAll(replace);
            output.add(translation);
        }
        return output;
    }


    public static void saveTxtFile(List<String> fullyFormatted){
        PrintWriter outPrinter= null;
        try{
            outPrinter = new PrintWriter(new FileWriter(outpath + "playlist.txt"));
            for(String song : fullyFormatted){
                outPrinter.println(song);
            }
        }catch (IOException e){
            System.out.println("Caught IOException" + e.getMessage());
        } finally{
            if (outPrinter != null){
                outPrinter.close();
            }
        }
    }
}
