import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

class Day1Part2 {
    public static void main(String[] args) throws IOException {
        var file = new File("inputs"+File.separator+"input-day1.txt");

        System.out.println(String.format("Reading file: %s", file.getAbsolutePath())); 
        var fileReader = new RandomAccessFile(file, "r");

        
        var column1 = new ArrayList<Integer>();
        var column2 = new ArrayList<Integer>();

        String currentLine;
        while((currentLine = fileReader.readLine()) != null){
            var split = currentLine.split(Pattern.quote("   "));
            column1.add(Integer.parseInt(split[0]));
            column2.add(Integer.parseInt(split[1]));
        }
        fileReader.close();

        Collections.sort(column1);
        Collections.sort(column2);

        var similarityScore = 0;
        for(var i=0; i< column1.size(); i++){
            similarityScore += column1.get(i) * Collections.frequency(column2, column1.get(i));
        }

        System.out.println(String.format("Similarity score: %d", similarityScore));
    }
}