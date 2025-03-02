package local.arch.controllers_admin.classes;

public class InfoAboutCenter {
    
    private String name_center;
    private String description;
    private Byte image_data;
    private String contacts;
    private String address;

    public String getName_center () {
        return this.name_center ;
    }

    public void setName_center(String name_center) {
        this.name_center = name_center;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte getImage_data() {
        return this.image_data;
    } 

    public void setImage_data(Byte image_data) {
        this.image_data = image_data;
    }

    public String getContacts() {
        return this.contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
