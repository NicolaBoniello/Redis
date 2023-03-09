package co.develhope.redisCache.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSuper {

    private String firstName;

    private String lastName;

    private String profilePicture;

    private String email;

    private String passwordEncrypted;

    private String domicileAddress;
    private String domicileCity;
    private String domicileState;
}
