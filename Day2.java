import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

class Day2 {
    public static void main(String[] args) throws IOException {
        var file = new File("inputs"+File.separator+"input-day2.txt");

        System.out.println(String.format("Reading file: %s", file.getAbsolutePath())); 
        var fileReader = new RandomAccessFile(file, "r");

        
        int safeTotal = 0;

        String currentLine;
        while((currentLine = fileReader.readLine()) != null){
            var split = currentLine.split(Pattern.quote(" "));

            List<Integer> deltas = new ArrayList<Integer>();
            int previous = Integer.parseInt(split[0]);
            for(int i=1; i < split.length; i++){
                int current = Integer.parseInt(split[i]);
                deltas.add(previous - current);
                previous = current;
            }
            if(Collections.min(deltas) >= 1 && Collections.max(deltas) <= 3){
                // all safely increasing
                safeTotal++;
            }else if(Collections.min(deltas) >= -3 && Collections.max(deltas) <= -1){
                // all safely decreasing
                safeTotal++;
            }
        }
        fileReader.close();

        System.out.println(String.format("Total safe levels: %d", safeTotal));
    }
}