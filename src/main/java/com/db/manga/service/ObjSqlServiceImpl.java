package com.db.manga.service;

import com.db.manga.dao.sql.RoleRepository;
import com.db.manga.dao.sql.UserRepository;
import com.db.manga.entity.sql.Role;
import com.db.manga.entity.sql.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ObjSqlServiceImpl implements ObjSqlService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private DataSource dataSource;

    @Autowired
    public ObjSqlServiceImpl(RoleRepository roleRepository, DataSource dataSource, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.dataSource = dataSource;
    }


    @Override
    public User getUserById(long id) {
        String sql = """
        SELECT 
            u.id, 
            u.user_data, 
            json_agg(json_build_object('id', r.id, 'roleDescription', r.role_description)) AS roles
        FROM 
            "user" u
        LEFT JOIN 
            user_role ur ON u.id = ur.user_id
        LEFT JOIN 
            role r ON ur.role_id = r.id
        WHERE 
            u.id = ?
        GROUP BY 
            u.id, u.user_data;
    """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Mapowanie użytkownika
                long userId = rs.getLong("id");
                String userData = rs.getString("user_data");

                //System.out.println("To jest userdata: " + userData);

                userData = userData.replace("(", "");
                userData = userData.replace(")", "");

                // Zakładając, że user_data jest w formacie JSON lub rozdzielonym przecinkami
                String[] parts = userData.split(",");

                User user = new User();
                user.setId(userId);
                user.setUsername(parts[0].trim());
                user.setPassword(parts[1].trim());
                user.setEmail(parts[2].trim());
                user.setSignupDate(parts[3].trim());


                // Mapowanie ról z JSON
                String rolesJson = rs.getString("roles");
                if (rolesJson != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Role> roles = objectMapper.readValue(rolesJson, new TypeReference<List<Role>>() {});
                    user.setRoles(roles);
                }

                return user;
            } else {
                return null;  // Brak użytkownika
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;  // Obsługa błędów
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUser(String userName, String password, String email, String signUpDate, List<Role> roles) {
        System.out.println("Creating User...");


        System.out.println("Creating User class...");
        User user = new User(userName, password, email,  signUpDate);

        List<Long> rolesList = new ArrayList<>();

        for (Role role : roles) {
            System.out.println("Searching for role...");

            Optional<Role> roleFound = roleRepository.findById(role.getId());
            System.out.println("Found role: " + roleFound);

            user.add(roleFound.get());
            rolesList.add(roleFound.get().getId());
        }

        Long[] roleIdsArray = rolesList.toArray(new Long[0]);

        System.out.println("User's roles: " + user.getRoles());

        System.out.println("Saving User: " + user);
        userRepository.addUserWithRoles(user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getSignupDate(),
                roleIdsArray);
        System.out.println("Successfully created User!");
    }



    public void updateUser(long userId, String username, String password, String email, String signUpDate) {

        User userFound = getUserById(userId);

        System.out.println("Updating User...");
        System.out.println("User before: " + userFound);


        userRepository.updateUser(userId, username, password, email, signUpDate);

        User userFoundAfter = getUserById(userId);
        System.out.println("User after: " + userFoundAfter);
    }

    public void deleteUser(long userId) {
        System.out.println("Deleting User...");

        User user = getUserById(userId);

        System.out.println("User found: " + user);

        System.out.println("Deleting User...");
        userRepository.deleteUser(user.getId());
    }

    @Override
    public void createRole(String roleDescription) {
        System.out.println("Creating Role...");
        Role role = new Role(roleDescription);
        System.out.println("Saving Role: " + role);
        roleRepository.save(role);
        System.out.println("Successfully created Role!");
    }

    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }
}
