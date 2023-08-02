package data;

public class Member extends Person {
    @Override
    public String toString() {
        return String.format("%s", getName());
    }
}
