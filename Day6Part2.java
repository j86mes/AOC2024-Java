import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


class Day6Part2 {

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

        Coord initialGuardPosition = new Coord();
        Direction initialGuardDirection = null;

        for(int y =0; y <  map.size(); y++){
            var row = map.get(y);
            for(int x = 0; x < row.length(); x++){
                if(row.charAt(x) != '.' && row.charAt(x) != '#'){
                    initialGuardPosition.x = x;
                    initialGuardPosition.y = y;
                    var g = new Guard();
                    g.setDirectionFromString(Character.toString(row.charAt(x)));
                    initialGuardDirection = g.direction;
                }
            }
        }

        var loopsFound = 0;
        for(int y = 0; y < map.size(); y++){
            for(int x = 0; x < map.get(y).length(); x++){
                var thisCell = map.get(y).charAt(x);

                if(thisCell == '.'){

                    System.out.println(String.format("testing: column %d row %d", x, y));
                    System.out.println(String.format("loops found: %d", loopsFound));

                    //create an obstacle.
                    map.get(y).setCharAt(x, '#');
                    
                    // make a brand new Guard2.
                    var guard = new Guard();
                    guard.direction = initialGuardDirection;
                    guard.setPosition(initialGuardPosition.x, initialGuardPosition.y);

                    var fallenOffTheEnd = false;
                    var looped = false;
                    while(!fallenOffTheEnd && !looped){

                        //PrintMap(map, Guard2); // this will print out the map view to console
                        //Thread.sleep(10); // this adds a slight delay so I can see the Guard2 moving around
                        try{
                            var nextCoord = guard.getNextStepCoords();
                            if(map.get(nextCoord.y).charAt(nextCoord.x) == '#'){
                                guard.turn();
                            }else{
                                //map.get(guard.position.y).setCharAt(guard.position.x, '~'); // track where we've been
                                boolean nextCoordIsUntouched = map.get(nextCoord.y).charAt(nextCoord.x) == '.';
                                guard.step(nextCoordIsUntouched); // tell the guard whether to tally up this cell or not
                            }

                            if(guard.isInALoopState()){
                                looped = true;
                            }
                        }catch (Exception e) {
                            fallenOffTheEnd = true;
                            System.out.println("Stepped: "+guard.cellsCovered);
                        }

                    }
                    if(looped){
                        loopsFound++;
                    }

                    // reset the map
                    map.get(y).setCharAt(x, '.');

                }
            }
        }
        System.out.println(String.format("Total loop causing obstacles found: %d", loopsFound));
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


    static class Coord{
        int x = 0;
        int y = 0;
    }

    static class CoordAndDirection{
        CoordAndDirection(Coord c, Direction d){
            this.coord = new Coord();
            this.coord.x = c.x;
            this.coord.y = c.y;
            this.direction = d;
        }
        Coord coord;
        Direction direction;
    }

    enum Direction{
        up, right, down, left
    }

    static class Guard{

        int steps = 0;
        int cellsCovered = 1; // count the current cell
        Direction direction = Direction.up;
        Coord position = new Coord();

        ArrayList<CoordAndDirection> history = new ArrayList<>();

        void setPosition(int x, int y){
            position.x = x;
            position.y = y;
            recordCurrentHistory();
        }

        void recordCurrentHistory(){
            var h = new CoordAndDirection(position, direction);
            history.add(h);
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
            recordCurrentHistory();
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

        boolean isInALoopState(){
            return history.stream().filter(h -> h.coord.x == position.x && h.coord.y == position.y && h.direction == direction).count() > 0;
        }
    }
}
