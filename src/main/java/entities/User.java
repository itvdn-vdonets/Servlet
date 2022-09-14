package entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
}
