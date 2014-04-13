package com.joker.dict.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.joker.dict.service.DictService;

@Path("/")
public class DictResource {
	@Inject
	private DictService dictService;
	
    @GET
    @Path("/dict")
    @Produces({ "application/json" })
    public String getDictDescription(@QueryParam("q") String text) {
        return "{\"desc\":\"" + dictService.getDictDescription(text) + "\"}";
    }
    
    @GET
    @Path("/vf")
    @Produces({ "application/json" })
    public String getVerbFormenDescription(@QueryParam("q") String text) {
        return "{\"desc\":\"" + dictService.getVerbFormenDescription(text) + "\"}";
    }
}
