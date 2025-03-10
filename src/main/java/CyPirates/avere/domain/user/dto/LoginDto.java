package CyPirates.avere.domain.user.dto;

import lombok.Getter;
import lombok.Setter;


public class LoginDto {
    private String username;
    private String password;

    public LoginDto() {

    }

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
