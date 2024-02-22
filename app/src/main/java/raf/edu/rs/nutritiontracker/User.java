package raf.edu.rs.nutritiontracker;


public class User {
    private String username;
    private String password;
    private String email;
    private int id;
    public User(String username,String password,int id){
        this.username=username;
        this.password=password;
        this.email=email;
        this.id=id;


    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static boolean isValidPassword(String password) {
        // Check length
        if (password.length() < 4) {
            return false;
        }

        // Check for uppercase letter and digit
        boolean hasUppercase = false;
        boolean hasDigit = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                // Special character found
                return false;
            }
        }

        // Check if password has at least one uppercase letter and one digit
      /*  if (!hasUppercase || !hasDigit) {
            return false;
        }
*/
        return true;
    }

}
