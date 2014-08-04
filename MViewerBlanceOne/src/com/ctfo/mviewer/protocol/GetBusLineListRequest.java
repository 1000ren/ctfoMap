package com.ctfo.mviewer.protocol;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.ctfo.mviewer.entity.Param;

import cm.framework.protocol.BaseHttpRequest;
import cm.framework.protocol.BaseHttpResponse;

public class GetBusLineListRequest extends BaseHttpRequest {

	private List<Param> params = new ArrayList<Param>();
	private String firstUrl = "http://59.108.127.194:8000/bussvc/index.php?" 
			+"getlinelist";
	public GetBusLineListRequest( String sid, String keyword, int page, int pagenum ,String admincode)
	{
		setMethod(POST);
		setAbsoluteURI( firstUrl);
		params.add(new Param("key", keyword));
		params.add(new Param("num", String.valueOf(pagenum)));
		params.add(new Param("page",String.valueOf(page)));
		params.add(new Param("sid", sid));
		params.add(new Param("sourse", "androidBx"));
		params.add(new Param("admincode", admincode));
	}
	
	public GetBusLineListRequest( String lineid,String sid)
	{
		setMethod(POST);
		setAbsoluteURI( firstUrl );
		params.add(new Param("lineid", lineid));
		params.add(new Param("sid", sid));
		params.add(new Param("sourse", "androidBx"));
	}

	@Override
	public BaseHttpResponse createResponse()
	{
		return new GetBusLineListResponse();
	}

	@Override
	public List<NameValuePair> getPostParams() 
	{
		List <NameValuePair> params = new ArrayList<NameValuePair>();
		for (Param param : this.params) 
		{
			params.add(new BasicNameValuePair(param.key, param.value));
		}
		return params;
	}

}
