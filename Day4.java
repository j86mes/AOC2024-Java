import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

class Day4 {
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
                // check each direction
                for(var v: directions){
                    try {
                        StringBuilder word = new StringBuilder();
                        for(int letter = 0; letter < 4; letter++){
                            var line = wordSearch.get( y + (v[1] * letter));
                            var character = line.charAt(x + (v[0] * letter));
                            word.append(character);
                        }
                        System.out.println(word.toString());
                        if(word.toString().equals("XMAS")){
                            wordsFound++;
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