import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Day12 {

    public Day12() {
    }

    private boolean isLowercase(String str) {
        for (int i = 0; i < str.length(); i++) {
            Character c = str.charAt(i);
            if (!Character.isLowerCase(c.charValue()))
                return false;
        }
        return true;
    }

    private HashMap<String, HashSet<String>> readInput(String filename) {
        HashMap<String, HashSet<String>> links = new HashMap<String, HashSet<String>>();

        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                int indexOfDash = line.indexOf("-");
                String node1 = line.substring(0, indexOfDash);
                String node2 = line.substring(indexOfDash + 1);

                HashSet<String> nodes;
                if (!links.containsKey(node1)) {
                    nodes = new HashSet<String>();
                    links.put(node1, nodes);
                }
                else
                    nodes = links.get(node1);
                nodes.add(node2);

                if (!links.containsKey(node2)) {
                    nodes = new HashSet<String>();
                    links.put(node2, nodes);
                }
                else
                    nodes = links.get(node2);
                nodes.add(node1);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return links;
    }


    public void searchPathRec(ArrayList<String> path, String newNode) {
        System.out.println("searchPathRec path=" + path + " newNode=" + newNode);

        if ("end".equals(newNode)) {
            path.add(newNode);
            paths.add(path);
            System.out.println("One path found: " + path);
            return;
        }

        if (!"start".equals(newNode) && isLowercase(newNode) && path.contains(newNode))
            return;

        for (String node : links.get(newNode)) {
            if ("start".equals(node))
                continue;
            ArrayList<String> newPath = new ArrayList<String>();
            newPath.addAll(path);
            newPath.add(newNode);
            searchPathRec(newPath, node);
        }
    }

    public void run() {
        //links = readInput("input_test_1.txt");
        //links = readInput("input_test_2.txt");
        //links = readInput("input_test_3.txt");
        links = readInput("input.txt");
        System.out.println("links="+links);

        paths = new HashSet<ArrayList<String>>();
        searchPathRec(new ArrayList<String>(), "start");
        System.out.println(paths.size() + " paths have been found.");

        ArrayList<ArrayList<String>> pathAsList = new ArrayList<ArrayList<String>>(paths);
        for (ArrayList<String> aPath : paths) {
            System.out.println("path=" + aPath);
        }
    }

    private HashMap<String, HashSet<String>> links;
    private HashSet<ArrayList<String>> paths;

    public static void main(String[] args) {
        Day12 d = new Day12();
        d.run();
    }
}
