import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

class Day2Part2 {
    public static void main(String[] args) throws IOException {
        var file = new File("inputs"+File.separator+"input-day2.txt");

        System.out.println(String.format("Reading file: %s", file.getAbsolutePath())); 
        var fileReader = new RandomAccessFile(file, "r");

        int safeTotal = 0;

        String currentLine;
        while((currentLine = fileReader.readLine()) != null){
            var split = currentLine.split(Pattern.quote(" "));

            // create a set of variations of the level, each with 1 entry missing.
            // might not be the most efficient, but it saves messing about.

            var levelVariations = new ArrayList<ArrayList<String>>();
            for(int i = 0; i < split.length; i++){
                levelVariations.add(new ArrayList<>(Arrays.asList(split)));
                levelVariations.get(i).remove(i);
            }

            for(var levelVariation : levelVariations ){
                List<Integer> deltas = new ArrayList<Integer>();
                for(int i=0, j=1; j < levelVariation.size(); i++, j++){
                    int previous = Integer.parseInt(levelVariation.get(i));
                    int current = Integer.parseInt(levelVariation.get(j));
                    deltas.add(previous - current);
                }

                if(Collections.min(deltas) >= 1 && Collections.max(deltas) <= 3){
                    // all safely increasing
                    safeTotal++;
                    break;
                }else if(Collections.min(deltas) >= -3 && Collections.max(deltas) <= -1){
                    // all safely decreasing
                    safeTotal++;
                    break;
                }
            }
            
        }
        fileReader.close();

        System.out.println(String.format("Total safe levels: %d", safeTotal));
    }
}