    package com.example.demo;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import org.mindrot.jbcrypt.BCrypt;

    import java.io.InputStream;
    import java.util.Arrays;
    import java.util.List;

    public class JsonHandlerUser {

        private static final String FILE_PATH = "/com/example/demo/liste_users.json";

        public static List<Utilisateur> loadUsers() {
            ObjectMapper objectMapper = new ObjectMapper();
            try (InputStream is = JsonHandlerUser.class.getResourceAsStream(FILE_PATH)) {
                if (is == null) return null;
                return Arrays.asList(objectMapper.readValue(is, Utilisateur[].class));
            } catch (Exception e) {
                return null;
            }
        }

        public static Utilisateur getUserByUsername(String username) {
            List<Utilisateur> users = loadUsers();
            if (users == null) return null;
            return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
        }

        public static boolean authenticate(String username, String password) {
            Utilisateur user = getUserByUsername(username);
            if (user == null) return false;
            return user.getPassword().equals(password);
        }
    }
