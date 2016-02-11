package net.cpollet.pocs.read.client.data;

/**
 * This is a simple DTO containing the result of call to {@link net.cpollet.pocs.readapi.api.AttributeService}
 * transformed through some {@link net.cpollet.pocs.readapi.helper.Transformer}.
 *
 * @author Christophe Pollet
 */
public class Person {
    private String firstName;
    private String lastName;
    private Integer age;
    private String catName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "String:firstName=" + firstName +
                ", String:lastName=" + lastName  +
                ", Integer:age=" + age +
                ", String:catName=" + catName +
                '}';
    }
}
