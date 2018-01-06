package com.sportsing.frontend;
import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import com.sportsing.api.Match;

@Named
@ViewScoped
public class SportsingBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private WebTarget matchesTarget = ClientBuilder.newClient().target("http://localhost:8080/webservice/matches");
	public List<Match> getMatches() {
		return matchesTarget.request(MediaType.APPLICATION_XML).get(new GenericType<List<Match>>(){});		
	}
	public List<String> getContenders() {
		return matchesTarget.path("contenders").request(MediaType.APPLICATION_XML).get(new GenericType<List<String>>(){});		
	}

	private Match newMatch;
	public Match getNewMatch() {
		if (newMatch == null) {
			newMatch = new Match(0,"Bupp");
			newMatch.registerContender("Lag1", 0, 0);
			newMatch.registerContender("Lag2", 0, 0);
		}
		return newMatch;
	}

	public void saveNewMatch() {
		matchesTarget.request(MediaType.APPLICATION_XML).post(Entity.xml(newMatch));
		newMatch = null;
	}
	public void cancelNewMatch() {
		newMatch = null;
	}
	
	public void deleteMatch(Match m) {
		matchesTarget.path("{id}").resolveTemplate("id", m.getId()).request(MediaType.APPLICATION_XML).delete();
	}
}
