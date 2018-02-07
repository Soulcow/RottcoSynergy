package synergy.rottco.bullet.black.rottcosynergy;

public class ModelUser {
    private String firstName;
    private String lastName;
    private String email;
    private String uuid;
    private String mobile;
    private String carBrand;
    private String carType;
    private String profilePicture;

    public ModelUser(String firstName, String lastName, String email, String uuid, String mobile, String carBrand, String carType, String profilePicture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.uuid = uuid;
        this.mobile = mobile;
        this.carBrand = carBrand;
        this.carType = carType;
        this.profilePicture = profilePicture;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUuid() {
        return uuid;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarType() {
        return carType;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString() {
        return "ModelUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", uuid='" + uuid + '\'' +
                ", mobile='" + mobile + '\'' +
                ", carBrand='" + carBrand + '\'' +
                ", carType='" + carType + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }
}
