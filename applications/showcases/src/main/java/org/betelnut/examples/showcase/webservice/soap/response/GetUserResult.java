package org.betelnut.examples.showcase.webservice.soap.response;

import org.betelnut.examples.showcase.webservice.soap.WsConstants;
import org.betelnut.examples.showcase.webservice.soap.response.base.WSResult;
import org.betelnut.examples.showcase.webservice.soap.response.dto.UserDTO;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "GetUserResult", namespace = WsConstants.NS)
public class GetUserResult extends WSResult {
	private UserDTO user;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
}
