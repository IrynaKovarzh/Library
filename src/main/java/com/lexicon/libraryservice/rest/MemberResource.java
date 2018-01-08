package com.lexicon.libraryservice.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lexicon.libraryservice.data.MembersDAOInterface;
import com.lexicon.libraryservice.model.*;

@Path("/member")
public class MemberResource {

	@Inject
	MembersDAOInterface dao;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveMember(Member member) throws URISyntaxException {
		dao.persistMember(member);
		return Response.created(new URI("localhost:8080/Library/rest/members")).build();
	}
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMembers() {
		return Response.ok(dao.getAllMembers()).build();
	}
}
