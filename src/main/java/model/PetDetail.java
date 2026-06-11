package model;

public class PetDetail {

    private int petID;
    private int categoryId;
    private String facilityID;
    private String categoryName;

    private int petInformationID;
    private String name;
    private String gender;
    private int age;
    private String color;
    private String pet_size;
    private String vaccine;
    private int price;
    private String commentText;

    // 画像
    private String imagePath;

    // 店舗情報
    private String facilityName;
    private String address;
    private String tel;

    public PetDetail(
            int petID,
            int categoryId,
            String facilityID,
            String categoryName,
            int petInformationID,
            String name,
            String gender,
            int age,
            String color,
            String pet_size,
            String vaccine,
            int price,
            String commentText,
            String imagePath,
            String facilityName,
            String address,
            String tel) {

        this.petID = petID;
        this.categoryId = categoryId;
        this.facilityID = facilityID;
        this.categoryName = categoryName;
        this.petInformationID = petInformationID;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.color = color;
        this.pet_size = pet_size;
        this.vaccine = vaccine;
        this.price = price;
        this.commentText = commentText;
        this.imagePath = imagePath;

        this.facilityName = facilityName;
        this.address = address;
        this.tel = tel;
    }

    public int getPetID() {
        return petID;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getFacilityID() {
        return facilityID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getPetInformationID() {
        return petInformationID;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getColor() {
        return color;
    }

    public String getPet_size() {
        return pet_size;
    }

    public String getVaccine() {
        return vaccine;
    }

    public int getPrice() {
        return price;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public String getAddress() {
        return address;
    }

    public String getTel() {
        return tel;
    }

    public String getPetSizeName() {

        if (pet_size == null) {
            return "";
        }

        switch (pet_size) {
        case "small":
            return "小型";
        case "medium":
            return "中型";
        case "large":
            return "大型";
        default:
            return pet_size;
        }
    }

    public String getVaccineName() {

        if (vaccine == null) {
            return "";
        }

        switch (vaccine) {
        case "vaccineDone":
            return "接種済み";
        case "vaccineYet":
            return "未接種";
        default:
            return vaccine;
        }
    }
}