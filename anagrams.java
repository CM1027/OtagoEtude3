import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.Comparator;

public class anagrams{
  public static ArrayList<String> TopList = new ArrayList<String>(); // topList
  public static ArrayList<String> anagramList = new ArrayList<String>();
  public static void main(String[] args) throws IOException{
    Scanner scan = new Scanner(System.in);
    //Scanner scan = new Scanner(new File(args[0]));
    ArrayList<String> TopList = new ArrayList<String>(); // topList
    ArrayList<String>  BottomList = new ArrayList<String>(); // bottomList
    
    
    //adds anagram words to an arraylist
    while(scan.hasNextLine()){
      String s = scan.nextLine();
      if(!s.equals("")){
        TopList.add(s.toLowerCase());
      }
      else{
        break;
      }
    }
    
    //adds dictionary words to an arraylist 
    while(scan.hasNextLine()){
      String s = scan.nextLine();
      BottomList.add(s.toLowerCase()); // MADE IT GO LOWER CASE FOR NOW
    }
    
    //sort bottomlist by length then alphabetically
    Collections.sort(BottomList, new MyComparator());
    
    //main loop
    for(int t = 0; t < TopList.size(); t++){
      ArrayList<String> ana = new ArrayList<String>();
      //this is our list of successful anagrams for toplist.get(t)
      anagramList = analist(TopList.get(t), BottomList);
      if(anagramList == null){
        System.out.println(TopList.get(t) + ":");
      }else{
//        System.out.println("Top " + TopList.get(t));
//        System.out.println(anagramList);
        int start = 0;
        ana = searchAnagram(TopList.get(t), start);
        if(ana == null){
          System.out.println(TopList.get(t) + ":");
        }else{
          Collections.reverse(ana);
          System.out.print(TopList.get(t) + ": ");
          for(int index = 0; index < ana.size(); index++){
            System.out.print(ana.get(index) + " ");
          }
          System.out.println();
        }
      }
    }
  }
  
  
  //check whether or not a string is an anagram of another string
  public static boolean search(String top, String bottom){
    boolean result = true;
    if(bottom.length() > top.length()){
      result = false;
      return result;
    }
    for (int q = 0; q < bottom.length(); q++) {
      char c = bottom.charAt(q); 
      String sC = Character.toString(c);
      if (!top.contains(sC)) {
        result = false;
        break;
      }
    }
    return result;
  }
  
  //get what is left of the toplist word after checking for anagrams
  public static String diff(String word, String remove){
    String remainder = word;
    for(int l = 0; l < remove.length(); l++){
      char c = remove.charAt(l);
      String r = Character.toString(c);
      remainder = remainder.replaceFirst(r, "");
    }
    return remainder;
  }
  
  //get all plausible anagrams for a word.
  public static ArrayList<String> analist(String top, ArrayList<String> bottom){
    ArrayList<String> anagrams = new ArrayList<String>();
    for(int i = 0; i < bottom.size(); i++){
      String b = bottom.get(i);
      if(search(top, b) == true){
        anagrams.add(b);
      }
    }
    if(anagrams.size() == 0){
      return null;
    }else{
      return anagrams;
    }
  }
  
  public static ArrayList<String> searchAnagram(String bigtext, int start){
    //if bigtext is empty we've finished
    if(bigtext.equals("")){
      return new ArrayList<String>();
    }
    //go through the arraylist
    for(int i = start; i < anagramList.size(); i++){
      //if anagramList is contained in bigtext
      if(search(bigtext, anagramList.get(i)) == true){
        //find the remainder
        String remainder = diff(bigtext, anagramList.get(i));
        //find what anagrams can be made out of the remainder
        ArrayList<String> rest = new ArrayList<String>();
        rest = searchAnagram(remainder, i);
        //if rest isn't null
        if(rest != null){
          //add current word to it
          rest.add(anagramList.get(i));
          return rest;
        }
      }
    }
    //if everything else failed
    return null;
  }
  //https://stackoverflow.com/questions/3408976/sort-array-first-by-length-then-alphabetically-in-java
  public static class MyComparator implements Comparator<String>{
    public int compare(String o1, String o2) {  
      if (o1.length() < o2.length()) {
        return 1;
      } else if (o1.length() > o2.length()) {
        return -1;
      }
      return o1.compareTo(o2);
    }
  }
}
