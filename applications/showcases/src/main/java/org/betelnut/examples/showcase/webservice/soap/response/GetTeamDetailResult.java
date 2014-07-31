package org.betelnut.examples.showcase.webservice.soap.response;

import org.betelnut.examples.showcase.webservice.soap.WsConstants;
import org.betelnut.examples.showcase.webservice.soap.response.base.WSResult;
import org.betelnut.examples.showcase.webservice.soap.response.dto.TeamDTO;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "GetTeamDetailResult", namespace = WsConstants.NS)
public class GetTeamDetailResult extends WSResult {

	private TeamDTO team;

	public GetTeamDetailResult() {
	}

	public GetTeamDetailResult(TeamDTO team) {
		this.team = team;
	}

	public TeamDTO getTeam() {
		return team;
	}

	public void setTeam(TeamDTO team) {
		this.team = team;
	}
}
