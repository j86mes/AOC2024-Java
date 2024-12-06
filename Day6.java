import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

class Day6 {


    public static void main(String[] args) throws IOException, InterruptedException {
        var file = new File("inputs"+File.separator+"input-day6.txt");

        System.out.println(String.format("Reading file: %s", file.getAbsolutePath())); 
        var fileReader = new RandomAccessFile(file, "r");

        ArrayList<StringBuilder> map = new ArrayList<StringBuilder>();

        String currentLine;
        while((currentLine = fileReader.readLine()) != null){
            map.add(new StringBuilder(currentLine));
        }
        fileReader.close();

        // create a guard and set his initial state.
        var guard = new Guard();
        for(int y =0; y <  map.size(); y++){
            var row = map.get(y);
            for(int x = 0; x < row.length(); x++){
                if(row.charAt(x) != '.' && row.charAt(x) != '#'){
                    guard.setPosition(x, y);
                    guard.setDirectionFromString(Character.toString(row.charAt(x)));
                    row.setCharAt(x, '.');
                }
            }
        }

        var fallenOffTheEnd = false;
        while(!fallenOffTheEnd){

            //PrintMap(map, guard); // this will print out the map view to console
            //Thread.sleep(10); // this adds a slight delay so I can see the guard moving around
            try{
                var nextCoord = guard.getNextStepCoords();
                if(map.get(nextCoord.y).charAt(nextCoord.x) == '#'){
                    guard.turn();
                }else{

                    map.get(guard.position.y).setCharAt(guard.position.x, '~'); // track where we've been
                    boolean nextCoordIsUntouched = map.get(nextCoord.y).charAt(nextCoord.x) == '.';
                    guard.step(nextCoordIsUntouched); // tell the guard whether to tally up this cell or not
                }
            }catch (Exception e) {
                fallenOffTheEnd = true;
            }
        }

        System.out.println(String.format("Total grid squares covered: %d", guard.cellsCovered));
    }
    
    static void PrintMap(ArrayList<StringBuilder> map, Guard guard){
        for(int y = 0; y < map.size(); y++){
            if(guard.position.y == y){
                map.get(y).setCharAt(guard.position.x, '@');
            }
            System.out.println(map.get(y).toString());
            if(guard.position.y == y){
                map.get(y).setCharAt(guard.position.x, '.');
            }
        }
        System.out.println("Steps: "+guard.steps);
        System.out.println("-----------------------------------------------------");
    }

}

class Coord{
    int x = 0;
    int y = 0;
}

class Guard{
    enum Direction{
        up, right, down, left
    }
    int steps = 0;
    int cellsCovered = 1; // count the current cell
    Direction direction = Direction.up;
    Coord position = new Coord();

    void setPosition(int x, int y){
        position.x = x;
        position.y = y;
    }

    void setDirectionFromString(String s){
        if(s.equals("^")){
            direction = Direction.up;
        }else if(s.equals(">")){
            direction = Direction.right;
        }else if(s.equals("v")){
            direction = Direction.down;
        }else{
            direction = Direction.left;
        }
    }

    void step(boolean unvisitedCell){
        steps++;
        if (unvisitedCell){
            cellsCovered++;
        }
        if(direction == Direction.up){
            position.y -= 1;
        }else if(direction == Direction.right){
            position.x += 1;
        }else if(direction == Direction.down){
            position.y += 1;
        }else{
            position.x -= 1;
        }
    }

    void turn(){
        if(direction == Direction.up){
            direction = Direction.right;
        }else if(direction == Direction.right){
            direction = Direction.down;
        }else if(direction == Direction.down){
            direction = Direction.left;
        }else{
            direction = Direction.up;
        }
    }

    Coord getNextStepCoords(){
        var next = new Coord();
        next.x = position.x;
        next.y = position.y;

        switch (direction) {
            case right:
                next.x += 1;
                break;
            case down:
                next.y += 1;
                break;
            case left:
                next.x -= 1;
                break;
            case up:
                next.y -= 1; 
                break;
        }

        return next;
    }
}