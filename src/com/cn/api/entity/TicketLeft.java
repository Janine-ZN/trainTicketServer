package com.cn.api.entity;

import net.sf.json.JSONObject;

public class TicketLeft {

	public JSONObject queryLeftNewDTO;
	public String secretStr;
	public String buttonTextInfo;

	public JSONObject getQueryLeftNewDTO() {
		return queryLeftNewDTO;
	}

	public void setQueryLeftNewDTO(JSONObject queryLeftNewDTO) {
		this.queryLeftNewDTO = queryLeftNewDTO;
	}

	public String getSecretStr() {
		return secretStr;
	}

	public void setSecretStr(String secretStr) {
		this.secretStr = secretStr;
	}

	public String getButtonTextInfo() {
		return buttonTextInfo;
	}

	public void setButtonTextInfo(String buttonTextInfo) {
		this.buttonTextInfo = buttonTextInfo;
	}

}
