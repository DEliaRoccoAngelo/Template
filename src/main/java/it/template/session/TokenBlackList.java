package it.template.session;

import java.util.ArrayList;
import java.util.List;

public class TokenBlackList {

	private List<String> tokens;
	
	private static TokenBlackList tokenBlackList;

	private TokenBlackList() {
		tokens = new ArrayList<String>();
	}
	
	public static TokenBlackList getIstance() {
		if(TokenBlackList.tokenBlackList == null) {
			tokenBlackList = new TokenBlackList();
		}
		return TokenBlackList.tokenBlackList;
	}

	public List<String> getTokens() {
		return tokens;
	}

	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}
	
	
}
