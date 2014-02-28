/**
 * Functionality 
 * 1. Using Httpclient for to crawl data by using HTTP GET
 * 2. Parser data (I was think about paralleling parser but for not many data there is no big speed improving)
 * 3. Using  backtrace algorithm and reducing the searching space for saving time
 * 4. Print the result and calculate time
 * 5. input should be "XXX number_of_gifts total_price searching_term product_limit(from 1-100)" (if limit == 0 mean no limit)
 * 
 * Testing:
 * It has to declare the searching term because if not there are too many data and too many request attempting that server will block my IP for a while. 
 * 1.
 * 	input: 3 35.2 boots 0
 * 	output:
   	There are 334 ways of providing gifts
   	the Max sum is:35.0
   	you chose:   number of gifts:3 |  total price:35.2 | term:boots | limit:0
   	products size:500
   	time of Algorithm: 0.202 second  | time of parser and getting data: 1.68 second  | Total run time : 1.883 second
   
   2.
    input: 3 35.2 boots 20
    Solution: 1
	ID: 2439289
	gift name: Cold Gear Lite Boot Sockgift price: 11.99
	ID: 2020995
	gift name: Dan Post Work & Outdoor High Performance Socks- Medium Weight Steel Toe 2-Packgift price: 11.99
	ID: 1848727
	gift name: Tall Boot Sockgift price: 11.0
	
	Solution: 2
	ID: 2439289
	gift name: Cold Gear Lite Boot Sockgift price: 11.99
	ID: 2020995
	gift name: Dan Post Work & Outdoor High Performance Socks- Medium Weight Steel Toe 2-Packgift price: 11.99
	ID: 1848719
	gift name: Tall Boot Sockgift price: 11.0
	
	Solution: 3
	ID: 2439289
	gift name: Cold Gear Lite Boot Sockgift price: 11.99
	ID: 2020977
	gift name: Dan Post Cowgirl Certified Over the Calf Socksgift price: 11.99
	ID: 1848727
	gift name: Tall Boot Sockgift price: 11.0
	
	Solution: 4
	ID: 2439289
	gift name: Cold Gear Lite Boot Sockgift price: 11.99
	ID: 2020977
	gift name: Dan Post Cowgirl Certified Over the Calf Socksgift price: 11.99
	ID: 1848719
	gift name: Tall Boot Sockgift price: 11.0
	
	Solution: 5
	ID: 2020995
	gift name: Dan Post Work & Outdoor High Performance Socks- Medium Weight Steel Toe 2-Packgift price: 11.99
	ID: 2020977
	gift name: Dan Post Cowgirl Certified Over the Calf Socksgift price: 11.99
	ID: 1848727
	gift name: Tall Boot Sockgift price: 11.0
	
	Solution: 6
	ID: 2020995
	gift name: Dan Post Work & Outdoor High Performance Socks- Medium Weight Steel Toe 2-Packgift price: 11.99
	ID: 2020977
	gift name: Dan Post Cowgirl Certified Over the Calf Socksgift price: 11.99
	ID: 1848719
	gift name: Tall Boot Sockgift price: 11.0
	
	There are 6 ways of providing gifts
	the Max sum is:34.98
	you chose:   number of gifts:3 |  total price:35.2 | term:boots | limit:20
	products size:20
	time of Algorithm: 0.033 second  | time of parser and getting data: 0.436 second  | Total run time : 0.469 second
*
*
*
* */


package Util;

import java.util.ArrayList;

public class Main 
{
	
	public static float Total_price;
	public static int Num_Product;
	public static String term="";
	public static int limit;
	
	static ArrayList<Products> gifts;
	public static void main(String args[])
	{
		//begin of the programming, record time
		long total_begin=System.currentTimeMillis();
		if(args.length!=4)
		{
			System.out.println("Wroing Input, It should be [number of products][total price][search terms][limit products]");
			return;
		}
		Num_Product = Integer.parseInt(args[0]);
		Total_price = Float.valueOf(args[1]);
		term = args[2];
		limit =Integer.parseInt( args[3]);
		
		//Crawler data
		long data_time_begin=System.currentTimeMillis();
		getData getdata = new getData(term,Num_Product,Total_price,limit);
		getdata.GetDataSocket();
		long data_time_end=System.currentTimeMillis();
		
		//Algorithm
		long algorithm_time_begin=System.currentTimeMillis();
		Algorthm algorithm = new Algorthm(getdata.getProducts());
		algorithm.getGifts(getdata.getProducts().size()-1, Total_price, Num_Product);
		algorithm.PrintResult();
		long algorithm_time_end=System.currentTimeMillis();
		
		System.out.println("you chose:   number of gifts:"+Num_Product+" | "+" total price:"+Total_price+" | term:"+term+" | "+ "limit:" +limit);
		System.out.println("products size:"+getdata.getProducts().size());
		System.out.print("time of Algorithm: "+(algorithm_time_end-algorithm_time_begin)/1000f+" second  | ");
		System.out.print("time of parser and getting data: "+(data_time_end-data_time_begin)/1000f+" second  | ");
		System.out.println("Total run time : "+(System.currentTimeMillis()-total_begin)/1000f+" second");
		
		
		
		
	}
	
	
}
