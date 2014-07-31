package org.betelnut.examples.showcase.webservice.soap.response;

import org.betelnut.examples.showcase.webservice.soap.WsConstants;
import org.betelnut.examples.showcase.webservice.soap.response.base.WSResult;
import org.betelnut.examples.showcase.webservice.soap.response.dto.UserDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "SearchUserResult", namespace = WsConstants.NS)
public class SearchUserResult extends WSResult {

	private List<UserDTO> userList;

	public SearchUserResult() {
	}

	public SearchUserResult(List<UserDTO> userList) {
		this.userList = userList;
	}

	@XmlElementWrapper(name = "userList")
	@XmlElement(name = "user")
	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}
}
