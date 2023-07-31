package data;

import lombok.Data;

@Data
public class Member extends Person {
    @Override
    public String toString() {
        return String.format("%s", getName());
    }
}
