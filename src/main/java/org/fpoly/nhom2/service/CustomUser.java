package org.fpoly.nhom2.service;

import java.util.Collection;
import java.util.List;

import org.fpoly.nhom2.entiry.CompanyAdmin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private org.fpoly.nhom2.entiry.User user;
    private List<CompanyAdmin> ca;
    private int defaultCompanyId;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, boolean enabled,
            org.fpoly.nhom2.entiry.User user, List<CompanyAdmin> ca) {
        super(username, password, enabled, true, true, true, authorities);
        this.user = user;
        this.ca = ca;
        if(ca != null && !ca.isEmpty()){
            this.defaultCompanyId = ca.get(0).getCompany().getCompanyId();
        }
    }

    public org.fpoly.nhom2.entiry.User getUser() {
        return user;
    }

    public void setUser(org.fpoly.nhom2.entiry.User user) {
        this.user = user;
    }

    public List<CompanyAdmin> getCa(){
        return ca;
    }

    public void setCa(List<CompanyAdmin> ca){
        this.ca = ca;
    }
    
    public int getDefaultCompanyId() {
        return defaultCompanyId;
    }

    public void setDefaultCompanyId(int defaultCompanyId) {
        this.defaultCompanyId = defaultCompanyId;
    }
}