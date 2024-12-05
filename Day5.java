import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

class Rule { 
    int before;
    int after;

    public Rule(int before, int after) { 
        this.before = before; 
        this.after = after; 
    } 
    public Rule(String before, String after) { 
        this.before = Integer.parseInt(before); 
        this.after = Integer.parseInt(after); 
    } 
} 

class Day5 {

    public static List<Integer> intersection(List<Integer> list1, List<Integer> list2) {
        List<Integer> list = new ArrayList<Integer>();
        for (Integer t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
        var file = new File("inputs"+File.separator+"input-day5.txt");

        System.out.println(String.format("Reading file: %s", file.getAbsolutePath())); 
        var fileReader = new RandomAccessFile(file, "r");

        var rules = new ArrayList<Rule>();
        var manuals = new ArrayList<List<Integer>>();

        String currentLine;
        while((currentLine = fileReader.readLine()) != null){
            if(currentLine.contains("|")){
                var split = currentLine.split(Pattern.quote("|"));
                rules.add(new Rule(split[0], split[1]));
            }else  if(!currentLine.equals("")){
                manuals.add(
                    Arrays.stream(currentLine.split(Pattern.quote(","))).map(s -> Integer.parseInt(s)).toList()
                );
            }
        }
        fileReader.close();

        var totalValidMiddles = 0;
        for(var manual: manuals){
            var valid = true;
            for(var i = 0; i < manual.size(); i++){
                int currentPageId = manual.get(i);
                var pagesBefore = manual.subList(0, i);
                var pagesAfter = manual.subList(i, manual.size());
                var beforeOnly = rules.stream().filter(r -> r.after == currentPageId).map(r -> r.before).toList();
                var afterOnly = rules.stream().filter(r -> r.before == currentPageId).map(r -> r.after).toList();
                if(intersection(pagesBefore, afterOnly).size() > 0 || intersection(pagesAfter, beforeOnly).size() > 0){
                    valid = false;
                }
            }
            if(valid){
                // valid
                totalValidMiddles += manual.get(manual.size()/2);
            }
        }

        System.out.println(String.format("Total middles: %d", totalValidMiddles));
    }
}