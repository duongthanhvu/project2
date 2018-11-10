package org.fpoly.nhom2.entiry;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 * Token
 */
@Entity
@NamedQuery(name="Token.findAll", query="SELECT t FROM Token t")
public class Token implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
	@SequenceGenerator(name="TOKEN_TOKENID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TOKEN_TOKENID_GENERATOR")
	@Column(name="idToken")
    private int tokenId;
    
    private String email;
    private String token;
    
    public Token(){}

    public Token(String email, String token){
        this.email = email;
        this.token = token;
    }

    /**
     * @return the tokenId
     */
    public int getTokenId() {
        return tokenId;
    }

    /**
     * @param tokenId the tokenId to set
     */
    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
}