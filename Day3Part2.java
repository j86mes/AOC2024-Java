import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Pattern;

class Day3Part2 {
    public static void main(String[] args) throws IOException {
        var file = new File("inputs"+File.separator+"input-day3.txt");

        System.out.println(String.format("Reading file: %s", file.getAbsolutePath())); 
        var fileReader = new RandomAccessFile(file, "r");

        String fullInput = "";
        String currentLine;
        while((currentLine = fileReader.readLine()) != null){
            fullInput = fullInput + "\n" + currentLine;
        }
        // I am incorrectly adding an empty line to the start but it won't affect outcome.
        
        fileReader.close();

        int total = 0;


        //lets just repeat the same process in batches. find batches between do and don't
        var textChunks = new ArrayList<String>();
        int currentChunkStart = 0; // the program starts in the enabled state
        int currentChunkEnd = 0;

        while(currentChunkStart > -1 &&  (currentChunkEnd = fullInput.indexOf("don't()", currentChunkStart)) > -1){
            textChunks.add(fullInput.substring(currentChunkStart, currentChunkEnd));
            currentChunkStart = fullInput.indexOf("do()", currentChunkEnd);
        }
        if(currentChunkStart > -1){
            // add on the last chunk if we ended with a do() but no subsequent don't()
            textChunks.add(fullInput.substring(currentChunkStart, currentChunkEnd));
        }

        for (var chunk : textChunks) {
            
            int previousIndex = 0;
            int currentIndex;
            while((currentIndex = chunk.indexOf("mul(", previousIndex+1)) != -1){

                System.out.println(String.format("mul( at: %d", currentIndex));
                int endCurrentPotentialMulIndex = chunk.indexOf(")", currentIndex);

                if(endCurrentPotentialMulIndex != -1){
                    try {
                        // plus 4 to chop off the mul(
                        var functionParamsString = chunk.substring(currentIndex+4, endCurrentPotentialMulIndex);
                        var twoParams = functionParamsString.split(Pattern.quote(","));
                        if(twoParams.length == 2){
                            total += Integer.parseInt(twoParams[0]) * Integer.parseInt(twoParams[1]);

                            System.out.println(String.format("Running total : %d", total));
                        }
                    } catch (Exception e) {
                        // it wasn't valid
                    }
                }

                previousIndex = currentIndex;
            }
        }

        System.out.println(String.format("Grand total : %d", total));
    }
}