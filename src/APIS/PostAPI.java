package APIS;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;


public interface PostAPI 
{
	public HttpResponse GETConnection(URI uri, String proxy, int port);
	public HttpResponse POSTConnection(URI uri,String proxy,int port,List <NameValuePair> nvps)throws UnsupportedEncodingException ;
	
}
