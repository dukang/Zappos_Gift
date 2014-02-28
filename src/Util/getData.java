package Util;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import APIS.PostAPI;
import Parser.Parser;
public class getData implements PostAPI
{
	private String term;
	private int number;
	private float price;
	private int limit;
	private ArrayList<Products> products;
	
	public ArrayList<Products> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Products> products) {
		this.products = products;
	}

	public getData(String term,int number,float price,int limit)
	{
		this.term = term;
		this.number = number;
		this.price = price;
		this.limit = limit;
		//initialize the products
		products = new ArrayList<Products>();
	}
	
	//USE URL PROXY AND PORT TO CREAT A RESPONSE
	public HttpResponse GETConnection(URI uri,String proxy,int port) 
	{
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpHost getproxy = new HttpHost(proxy,port); 
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, getproxy);
			HttpGet httpget = new HttpGet(uri);
			HttpResponse response=null;
			try 
			{
				response = httpclient.execute(httpget);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return response;
	}
	 
	public HttpResponse POSTConnection(URI uri, String proxy, int port,List <NameValuePair> nvps) throws UnsupportedEncodingException 
	{
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpHost getproxy = new HttpHost(proxy,port); 
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, getproxy);
			HttpPost httppost=new HttpPost(uri);
			httppost.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse response=null;
			try 
			{
				response = httpclient.execute(httppost);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return response;
	}

	
	@SuppressWarnings("deprecation")
	public void GetDataSocket()
	{
		//set default page 
		int page = 0;
		
		//initialization
		Parser parser = new Parser();
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = null;
		URI uri = null;
		HttpGet httpget;
		InputStream input = null;
		
		//build uri parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();  
		// limit the products number for testing limit should be 1-100
		if(limit !=0)
		{
			//update page
			page=1;
			
			//building the uri
			params.clear();
			params.add(new BasicNameValuePair("term", term));   
			params.add(new BasicNameValuePair("key", "a73121520492f88dc3d33daf2103d7574f1a3166"));   
			params.add(new BasicNameValuePair("page", page+""));  
			params.add(new BasicNameValuePair("limit", limit+""));
			params.add(new BasicNameValuePair("sort", "{\"price\":\"asc\"}"));
			String param = URLEncodedUtils.format(params, "UTF-8"); 
			try 
			{
				uri = URIUtils.createURI("http", "api.zappos.com", -1, "/Search/",param, null);
			} catch (URISyntaxException e1) 
			{
				e1.printStackTrace();
			}
			//GET request
			httpget = new HttpGet(uri);
			
			//execute GET request
			try
			{
				response = httpclient.execute(httpget);
			}catch(ClientProtocolException e)
			{
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			String status = response.getStatusLine().toString();
			status = status.substring(status.lastIndexOf("1.1")+4,status.lastIndexOf("1.1")+7);
			//System.out.println(status);
			//check status if it is 200
			if(Integer.parseInt(status)==HttpStatus.SC_OK)
			{
				//if 200, do the parsing
				try 
				{
					input = response.getEntity().getContent();
					products.addAll(parser.ParserText(input));
				} catch (IllegalStateException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
			}else
			{
				System.out.println("connect error status: "+status); 
				
			}
		}else//limit = 0, it means search for all data less than price
		{
			while(true)
			{
				//update page
				page++;
				
				//building the uri
				params.clear();
				params.add(new BasicNameValuePair("term", term));   
				params.add(new BasicNameValuePair("key", "a73121520492f88dc3d33daf2103d7574f1a3166"));   
				params.add(new BasicNameValuePair("page", page+""));  
				params.add(new BasicNameValuePair("limit", "100"));
				params.add(new BasicNameValuePair("sort", "{\"price\":\"asc\"}"));
				String param = URLEncodedUtils.format(params, "UTF-8"); 
				try 
				{
					uri = URIUtils.createURI("http", "api.zappos.com", -1, "/Search/",param, null);
				} catch (URISyntaxException e1) 
				{
					e1.printStackTrace();
				}
				
				//GET request
				httpget = new HttpGet(uri);
				
				//execute GET request
				try
				{
					response = httpclient.execute(httpget);
				}catch(ClientProtocolException e)
				{
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				String status = response.getStatusLine().toString();
				status = status.substring(status.lastIndexOf("1.1")+4,status.lastIndexOf("1.1")+7);
				
				//check status if it is 200
				if(Integer.parseInt(status)==HttpStatus.SC_OK)
				{
					//if 200, do the parsing
					try 
					{
						
						input = response.getEntity().getContent();
						products.addAll(parser.ParserText(input));
						
					} catch (IllegalStateException e) {
						
						e.printStackTrace();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
				}else
				{
					System.out.println("connect error status: "+status); 
					
				}
				
				//won't stop until get the price larger than user input
				if(products.get(products.size()-1).getPrice()>price) break;
				
			}
			
			System.out.println();
		}
	}
}
