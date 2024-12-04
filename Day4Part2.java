import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

class Day4Part2 {
    public static void main(String[] args) throws IOException {
        var file = new File("inputs"+File.separator+"input-day4.txt");

        System.out.println(String.format("Reading file: %s", file.getAbsolutePath())); 
        var fileReader = new RandomAccessFile(file, "r");

        // directions
        int[][] directions = {
            {1, 0}, // right
            {1, -1}, // down right
            {0, -1}, // down
            {-1, -1}, // down left
            {-1, 0}, // left
            {-1, 1}, // up left
            {0, 1}, // up
            {1, 1} // up right
        };

        String currentLine;
        ArrayList<String> wordSearch = new ArrayList<String>();
        while((currentLine = fileReader.readLine()) != null){
            wordSearch.add(currentLine);
        }
        fileReader.close();

        var width  = wordSearch.get(0).length();
        var height = wordSearch.size();

        var wordsFound = 0;

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){

                // if A, check for the MASes

                var line = wordSearch.get(y);
                var character = line.charAt(x);
                if(character == 'A'){
                    try {
                        StringBuilder firstStroke = new StringBuilder();
                        firstStroke.append(wordSearch.get(y-1).charAt(x-1));
                        firstStroke.append('A');
                        firstStroke.append(wordSearch.get(y+1).charAt(x+1));
                        if(firstStroke.toString().equals("MAS") || firstStroke.toString().equals("SAM") ){

                            StringBuilder secondStroke = new StringBuilder();
                            
                            secondStroke.append(wordSearch.get(y-1).charAt(x+1));
                            secondStroke.append('A');
                            secondStroke.append(wordSearch.get(y+1).charAt(x-1));
                            if(secondStroke.toString().equals("MAS") || secondStroke.toString().equals("SAM") ){

                                wordsFound++;
                            }
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
        }

        System.out.println(String.format("Total words found: %d", wordsFound));
    }
}