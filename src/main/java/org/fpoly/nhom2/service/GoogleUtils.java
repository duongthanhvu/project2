package org.fpoly.nhom2.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.fpoly.nhom2.repository.RoleRepository;
import org.fpoly.nhom2.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GoogleUtils {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
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
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        List<org.fpoly.nhom2.entiry.User> users = userRepository.findByEmail(googlePojo.getEmail());
        if (users == null || users.isEmpty()) {
            org.fpoly.nhom2.entiry.User newUser = new org.fpoly.nhom2.entiry.User();
            newUser.setEmail(googlePojo.getEmail());
            newUser.setRole(roleRepository.getOne(3));
            newUser.setUsername(googlePojo.getEmail().substring(0, googlePojo.getEmail().indexOf("@")));
            newUser.setPassword(UUID.randomUUID().toString());
            //newUser.setAvatar(downloadGmailProfilePicture(googlePojo));
            userRepository.save(newUser);
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            UserDetails userDetail = new User(newUser.getUsername(), newUser.getPassword(), enabled, accountNonExpired,
                    credentialsNonExpired, accountNonLocked, authorities);
            return userDetail;
        } else {
            org.fpoly.nhom2.entiry.User user = users.get(0);
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            UserDetails userDetail = new User(user.getUsername(), user.getPassword(), enabled, accountNonExpired,
                    credentialsNonExpired, accountNonLocked, authorities);
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