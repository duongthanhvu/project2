package org.fpoly.nhom2.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.fpoly.nhom2.entiry.CompanyAdmin;
import org.fpoly.nhom2.entiry.Profile;
import org.fpoly.nhom2.repository.CompanyAdminRepository;
import org.fpoly.nhom2.repository.ProfileRepository;
import org.fpoly.nhom2.repository.RoleRepository;
import org.fpoly.nhom2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class GoogleUtils {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CompanyAdminRepository caRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private Environment env;

    public String getToken(final String code) throws IOException {
        String link = env.getProperty("google.link.get.token");
        String response = Request.Post(link)
                .bodyForm(Form.form().add("client_id", env.getProperty("google.app.id"))
                        .add("client_secret", env.getProperty("google.app.secret"))
                        .add("redirect_uri", env.getProperty("google.redirect.uri")).add("code", code)
                        .add("grant_type", "authorization_code").build())
                .execute().returnContent().asString();
        System.out.println("get access token...\n" + response);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("access_token");
        return node.textValue();
    }

    public GooglePojo getUserInfo(final String accessToken) throws IOException {
        String link = env.getProperty("google.link.get.user_info") + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        System.out.println("get user info...\n" + response);
        ObjectMapper mapper = new ObjectMapper();
        GooglePojo googlePojo = mapper.readValue(response, GooglePojo.class);
        System.out.println(googlePojo);
        return googlePojo;
    }

    public UserDetails builUser(GooglePojo googlePojo) {
        boolean enabled = true;
        List<org.fpoly.nhom2.entiry.User> users = userRepository.findByEmail(googlePojo.getEmail());
        if (users == null || users.isEmpty()) {
            org.fpoly.nhom2.entiry.User newUser = new org.fpoly.nhom2.entiry.User();
            newUser.setEmail(googlePojo.getEmail());
            newUser.setRole(roleRepository.getOne(3));
            newUser.setEnabled(true);
            newUser.setUsername(googlePojo.getEmail().substring(0, googlePojo.getEmail().indexOf("@"))+"_controln");
            newUser.setPassword(UUID.randomUUID().toString());
            userRepository.save(newUser);
            Profile profile = new Profile();
            profile.setProfileId(newUser.getUserId());
            profile.setFullname(googlePojo.getName());
            profile.setUrlName(newUser.getUsername());
            profile.setAvatarPicture(downloadGmailProfilePicture(googlePojo));
            if(googlePojo.getGender().equals("male")){
                profile.setGender("M");
            }else{
                profile.setGender("F");
            }
            profileRepository.save(profile);
            newUser = userRepository.getOne(newUser.getUserId());
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_user"));
            UserDetails userDetail = new CustomUser(newUser.getUsername(), newUser.getPassword(), authorities, enabled, newUser, newUser.getCompanyAdmins());
            return userDetail;
        } else {
            org.fpoly.nhom2.entiry.User user = users.get(0);
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole().getName()));
            List<CompanyAdmin> ca = caRepository.findByUser(user);
            UserDetails userDetail = new CustomUser(user.getUsername(), user.getPassword(), authorities, enabled, user, ca);
            return userDetail;
        }
    }

    private String downloadGmailProfilePicture(GooglePojo googlePojo) {
        try {
            URL url = new URL(googlePojo.getPicture());
            String newAvatar = UUID.randomUUID().toString() + ".jpg";
            File dest = new File(env.getProperty("vitriluufile") + "/" + newAvatar);
            FileUtils.copyURLToFile(url, dest);
            return newAvatar;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}