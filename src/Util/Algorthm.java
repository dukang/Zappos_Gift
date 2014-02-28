package Util;

import java.util.ArrayList;

public class Algorthm 
{
	//gift is the list of the products as a gift
	ArrayList<Products> gift; 
	//gift_list is the list of gift
	ArrayList<ArrayList<Products>> gift_list;
	//all the products
	ArrayList<Products> products;

	float minSum=100000000.0f;
	
	//initialization
	public Algorthm(ArrayList<Products> products) 
	{
		
		this.products=products;
		gift = new ArrayList<Products>();
		gift_list = new ArrayList<ArrayList<Products>>();
	}
	
	//Use the tree structure recursively call itself find the smallest value and record it.
	//during the process, reduce the searching space for saving time. 
	public boolean getGifts(int index,float sum, int n)
	{
		
		if((sum>=0&&n==0)) 
		{
			if(sum<minSum)
			{
				gift_list.clear();
				ArrayList<Products>tmp = new ArrayList<Products>();
			
				for(int i=0;i<gift.size();i++)
				{
					tmp.add(gift.get(i));
				}
				
				gift_list.add(tmp);
				
				minSum = sum;
				return true;
			}else if(sum==minSum)
			{
				ArrayList<Products>tmp = new ArrayList<Products>();
				
				for(int i=0;i<gift.size();i++)
				{
					
					tmp.add(gift.get(i));
					
				}
				
				gift_list.add(tmp);
				return true;
			}
			
			
		}
		
		//reduce the searching space
		if((sum<0) ||index<n-1 ||index <=-1)
		{
			return false;
		}
		
		gift.add(products.get(index));
		getGifts(index-1,sum-products.get(index).getPrice(),n-1);
		gift.remove(gift.size()-1);
		
		return getGifts(index-1,sum,n);

	}
	
	//print the result
	public void PrintResult()
	{
		float sum =0;
		for(int i=0;i<gift_list.size();i++)
		{
			System.out.println("Solution: "+(i+1));
			for(int j=0;j<gift_list.get(i).size();j++)
			{
				System.out.println("ID: "+gift_list.get(i).get(j).getId());
				System.out.println("gift name: "+gift_list.get(i).get(j).getName() + "gift price: "+gift_list.get(i).get(j).getPrice());
			}
			System.out.println();
		}
		System.out.println("There are "+ gift_list.size()+" ways of providing gifts");
		
		int n = gift_list.size();
		for(int i=0;i<gift_list.get(n-1).size();i++)
		{
			sum+=gift_list.get(n-1).get(i).getPrice();
		}
		System.out.println("the Max sum is:"+sum);
	}
}
