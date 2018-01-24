
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.*;
public class App {

    public static String header = new String();
    public static ArrayList<String> headerliste = new ArrayList<String>();

    public static void main(String[] args) throws Exception {

        String in = ("src/main/java/dataset.csv");  //lecture fichier
        String out = ("src/data.json");

        File file = new File(in);
        File file2 = new File(out);
        InputStreamReader ini = new InputStreamReader(new FileInputStream(file));
        @SuppressWarnings("resource")
        BufferedReader iii = new BufferedReader(ini);
        header = iii.readLine();
        System.out.println(header);
        headerliste = linetolist(header);
        System.out.println(headerliste.get(0));
        System.out.println(headerliste.get(1));
        System.out.println(headerliste.get(2));
        System.out.println(headerliste.get(3));

        FileOutputStream fo = new FileOutputStream(file2);
        boolean f = true;
        fo.write("{".getBytes("UTF-8"));
        while(f==true){

            String l = new String();
            l = iii.readLine();
            if (l==null)
                break;
            System.out.println(l);
            ArrayList<String> ligne = linetolist(l);
            String retour="{";
            retour = CSVtoJSON(ligne);

            fo.write(retour.getBytes("UTF-8"));}
        fo.write("}".getBytes("UTF-8"));
        fo.close();
    }

    public static ArrayList<String> linetolist (String line){


        ArrayList<String> liste = new ArrayList<String>();
        int d = 0;
        int f = 0;
        System.out.println(line);
        for(int i = 0; i<line.length();i++) {
            if (line.charAt(i) == '|') {
                f = i;
                liste.add(line.substring(d,f));
                d = f+1;
            }

        }

        liste.add(line.substring(d,line.length()));

        return liste;

    }

    public static String CSVtoJSON(ArrayList<String> ligne) {
        String ligne1 ="\n[";

        for (int i = 0; i<headerliste.size()-1;i++) {
            String glide = ligne.get(i).replaceAll("\"", " ");
            System.out.println(glide);
            if (i==0) {
                ligne1 = ligne1 + headerliste.get(i) + " : "+"\"" + glide.toUpperCase() +"\",";
            } else if(i==1){
                ligne1 = ligne1  + headerliste.get(i) + ":"+"\"" + glide.substring(0, 1).toUpperCase() + glide.substring(1) +" \",";

            }else if(i==2) {
                String[] r = glide.split("-");

                ligne1 = ligne1  + headerliste.get(i) + ":" + "\""+r[2]+"-"+r[1]+"-"+r[0] +"\" ,";
            }
            else if(i==3) {
                int s = ligne.get(i).indexOf(".");
                ligne1 = ligne1 + headerliste.get(i) + " : " +"\"" +glide.substring(0,s+3) +"\" ";
            }
        }
        ligne1 = ligne1 + "],";
        System.out.println(ligne1);
        return ligne1;
    }}