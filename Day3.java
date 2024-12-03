import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Pattern;

class Day3 {
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

        int previousIndex = 0;
        int currentIndex;
        while((currentIndex = fullInput.indexOf("mul(", previousIndex+1)) != -1){

            System.out.println(String.format("mul( at: %d", currentIndex));
            int endCurrentPotentialMulIndex = fullInput.indexOf(")", currentIndex);

            if(endCurrentPotentialMulIndex != -1){
                try {
                    // plus 4 to chop off the mul(
                    var functionParamsString = fullInput.substring(currentIndex+4, endCurrentPotentialMulIndex);
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
        System.out.println(String.format("Grand total : %d", total));
    }
}