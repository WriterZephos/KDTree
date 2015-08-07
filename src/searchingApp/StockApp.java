package searchingApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

/*************************************************************************
 *  Compilation:  javac LookupCSV.java
 *  Execution:    java LookupCSV file.csv keyField valField
 *  Dependencies: ST.java In.java StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/35applications/DJIA.csv
 *                http://algs4.cs.princeton.edu/35applications/UPC.csv
 *                http://algs4.cs.princeton.edu/35applications/amino.csv
 *                http://algs4.cs.princeton.edu/35applications/elements.csv
 *                http://algs4.cs.princeton.edu/35applications/ip.csv
 *                http://algs4.cs.princeton.edu/35applications/morse.csv
 *  
 *  Reads in a set of key-value pairs from a two-column CSV file
 *  specified on the command line; then, reads in keys from standard
 *  input and prints out corresponding values.
 * 
 *  % java LookupCSV amino.csv 0 3     % java LookupCSV ip.csv 0 1 
 *  TTA                                www.google.com 
 *  Leucine                            216.239.41.99 
 *  ABC                               
 *  Not found                          % java LookupCSV ip.csv 1 0 
 *  TCT                                216.239.41.99 
 *  Serine                             www.google.com 
 *                                 
 *  % java LookupCSV amino.csv 3 0     % java LookupCSV DJIA.csv 0 1 
 *  Glycine                            29-Oct-29 
 *  GGG                                252.38 
 *                                     20-Oct-87 
 *                                     1738.74
 *
 *
 *************************************************************************/

public class StockApp {
    public static void main(String[] args) {

        // symbol table

        // read in the data from csv file
        RedBlackBST<Date, Double> st;
        RedBlackBST<Date, Double> newSt;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			st = readInDataFromCsv(4,"/Toyota.csv");
			newSt = readInDataFromCsv(1,"/Toyota.csv"); 
			StdOut.println("Number of record read: " + st.size());
			
			Date first = format.parse("2012-03-12");
			Date second = format.parse("2015-03-12");
			Date leastRecent = st.min();
			Date mostRecent = st.max();
			Date firstIn2005 = st.ceiling(format.parse("2004-12-31"));

			Date lastIn2009 = newSt.floor(format.parse("2010-01-01"));
			Date lastOpening = newSt.max();
			
			StdOut.println("\nClosing price on " + format.format(first) + ": " + st.get(first));
			StdOut.println("\nClosing price on " + format.format(second) + ": " + st.get(second));
			StdOut.println("\nClosing price on least recent date" + format.format(leastRecent) + ": " + st.get(leastRecent));
			StdOut.println("\nClosing price on most recent date" + format.format(mostRecent) + ": " + st.get(mostRecent));
			StdOut.println("\nFirst closing price in 2005" + format.format(firstIn2005) + ": " + st.get(firstIn2005));
			StdOut.println("\n");
			
			Iterable<Date> all2012 = st.keys(format.parse("2012-01-01"),format.parse("2013-01-01"));
			all2012.forEach(d -> StdOut.println("2012 Closing:  " + format.format(d) + ": " + st.get(d)));
			
			StdOut.println("\n");
			
			Iterable<Date> allOpening2012 = newSt.keys(format.parse("2012-01-01"),format.parse("2013-01-01"));
			allOpening2012.forEach(d -> StdOut.println("2012 opening: " + format.format(d) + ": " + newSt.get(d)));
			
			
			
			StdOut.println("\nLast opening price in 2009" + format.format(lastIn2009) + ": " + st.get(lastIn2009));
			StdOut.println("\nMost recent opening price: " + format.format(lastOpening) + ": " + st.get(lastOpening));
			
			
			while (!StdIn.isEmpty()) {
				Date s;
				s = format.parse(StdIn.readString());
				if (st.contains(s)) StdOut.println(st.get(s));
				else  StdOut.println("Not found");
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        
        
        
    }
    
    private static RedBlackBST<Date, Double> readInDataFromCsv(int valField, String stockFile) throws ParseException{
        In in = new In(stockFile);
        if(in.hasNextLine()) in.readLine();
        RedBlackBST<Date, Double> st = new RedBlackBST<>();
        final int key = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            st.put(format.parse(tokens[key]), Double.parseDouble(tokens[valField]));
        }
        return st;
    }
}
