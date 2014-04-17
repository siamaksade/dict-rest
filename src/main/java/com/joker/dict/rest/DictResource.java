package com.joker.dict.rest;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.joker.dict.model.Noun;
import com.joker.dict.model.Verb;
import com.joker.dict.model.Word;
import com.joker.dict.service.DictService;
import com.joker.dict.service.VerbFormService;

@Path("/")
public class DictResource {
	@Inject
	private DictService dictService;

	@Inject
	private VerbFormService verbFormService;
	
    @GET
    @Path("/dict")
    @Produces({ "application/json" })
    public QueryResponse<Noun> getDictDescription(@QueryParam("q") String text) {
        try {
			Noun noun = dictService.getDictDescription(text);
			
			if (noun != null) {
				return QueryResponse.ok(noun);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return QueryResponse.empty(e.getMessage());
		}
        
        return QueryResponse.empty("not found");
    }
    
    @GET
    @Path("/vf")
    @Produces({ "application/json" })
    public QueryResponse<Verb> getVerbFormenDescription(@QueryParam("q") String text) {
        try {
			Verb verb = verbFormService.getVerbFormenDescription(text);
			
			if (verb != null) {
				return QueryResponse.ok(verb);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return QueryResponse.empty(e.getMessage());
		}
        
        return QueryResponse.empty("not found");
    }
    
    public static class QueryResponse<T extends Word> {
    	public static final QueryResponse<Word> NOT_FOUND = new QueryResponse<Word>(null, "not found");
    	
    	private T result;
    	private String status;
    	
    	public static <T extends Word> QueryResponse<T> empty(String status) {
    		return new QueryResponse<T>(null, status);
    	}
    	
    	public static <T extends Word> QueryResponse<T> ok(T word) {
    		return new QueryResponse<T>(word, "ok");
    	}
    	
		private QueryResponse(T result, String status) {
			this.result = result;
			this.status = status;
		}
		
		public T getResult() {
			return result;
		}
		
		public String getStatus() {
			return status;
		}
    }
}
