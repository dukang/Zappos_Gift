package Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import Util.Products;

public class Parser 
{
	
	public Parser() 
	{
		
	} 
	public ArrayList<Products> ParserText(InputStream input) throws IOException
	{
		//some fixed field
		String id_str = "styleId";
		String name_str = "productName";
		String price_str = "price";
		ArrayList<Products> products = new ArrayList<Products>();
		Products product;
		String content = ReadStream(input);
		
		//split the data
		String tmp[] = content.split("},");
		String product_split[];
		String id="";
		String name="";
		float price=0;
		
		int count =0;
		for(int i=0; i<tmp.length;i++)
		{
			count=0;
			String str_tmp;
			//split product attributes
			product_split = tmp[i].split(",");
			for(int j=0;j<product_split.length;j++)
			{
				if(count ==3) break;//reduce the loop time
				if(product_split[j].indexOf(id_str)!=-1)
				{
					count++;
					//get id
					str_tmp = product_split[j].substring(product_split[j].indexOf(":")+2,product_split[j].length()-1);
					id = str_tmp;
				}
				if(product_split[j].indexOf(name_str)!=-1)
				{
					count++;
					//get name
					str_tmp = product_split[j].substring(product_split[j].indexOf(":")+2,product_split[j].length()-1);
					name = str_tmp;
				}
				if(product_split[j].indexOf(price_str)!=-1)
				{
					count++;
					//get price
					str_tmp =  product_split[j].substring(product_split[j].indexOf("$")+1, product_split[j].length()-1);
					price = Float.parseFloat(str_tmp);
				}
			}
			product = new Products(id,name,price);
			products.add(product);
		}
		return products;
	}
	
	//read stream into string
	public static String ReadStream(InputStream input) throws IOException
	{
		StringBuffer strbuffer= new StringBuffer();
		InputStreamReader inpustream = new InputStreamReader(input); 
		BufferedReader buffer = new BufferedReader(inpustream);
		String str="";
		while((str=buffer.readLine())!=null)
		{
			strbuffer.append(str);
			strbuffer.append("\r\n");
		}
		return strbuffer.toString();
	}
}
