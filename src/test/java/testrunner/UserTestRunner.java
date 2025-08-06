package testrunner;

import controller.userController;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import setup.AddItemModel;
import setup.Setup;
import setup.UserModel;
import utils.Utils;

public class UserTestRunner extends Setup {
    private userController userController;

    @BeforeMethod
    public void initUserController() {
        userController = new userController(prop);
    }
    @Test(priority = 1, description = "User Registration With Missing Fields")
    public void RegistrationWithMissingFeilds() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {

        String firstName = Utils.getRandomFirstName();
        String lastName = Utils.getRandomLastName();
//        String email = Utils.getRandomEmail();
//        String password = Utils.getPassword();
//        String phoneNumber = Utils.getRandomPhoneNumber();
        String address = Utils.getRandomAddress();
        String gender = Utils.getRandomGender();

        UserModel userModel = new UserModel();
        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
//        userModel.setEmail(email);
//        userModel.setPassword(password);
//        userModel.setPhoneNumber(phoneNumber);
        userModel.setAddress(address);
        userModel.setGender(gender);
        userModel.setTermsAccepted(true);

        Response res = userController.doRegister(userModel);
        System.out.println(res.asString());

        Assert.assertEquals(res.getStatusCode(), 500);
        Assert.assertTrue(res.asString().contains("Server error"));
        System.out.println("User Cannot Login Without Filling Up Necessary Fields!");
    }



     @Test(priority = 2,description = "User Registration")
    public void doRegistration() throws ConfigurationException {
        UserModel userModel = new UserModel();
        String firstName = Utils.getRandomFirstName();
        String lastName = Utils.getRandomLastName();
        String email = Utils.getRandomEmail();
        String password = Utils.getPassword();
        String phoneNumber = Utils.getRandomPhoneNumber();
        String address = Utils.getRandomAddress();
        String gender = Utils.getRandomGender();

        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhoneNumber(phoneNumber);
        userModel.setAddress(address);
        userModel.setGender(gender);

        Response res = userController.doRegister(userModel);
        System.out.println(res.asString());
        JsonPath jsonPath = res.jsonPath();

        String userId = jsonPath.get("_id");
        Utils.setEnvVar("UserID", userId);
        String userFirstName = jsonPath.get("firstName");
        Utils.setEnvVar("userFirstName", userFirstName);

        String userLastName = jsonPath.get("lastName");
        Utils.setEnvVar("userLastName", userLastName);

        String userEmail = jsonPath.get("email");
        Utils.setEnvVar("userEmail", userEmail);

        Utils.setEnvVar("userPassword", password);

        Assert.assertEquals(res.getStatusCode(), 201);

    }

    @Test(priority = 3,description = "User Login")
    public void userLogin() throws ConfigurationException {
        UserModel userModel = new UserModel();
        userModel.setEmail(prop.getProperty("userEmail"));
        userModel.setPassword(prop.getProperty("userPassword"));

        Response res = userController.doLogin(userModel);
        JsonPath jsonPath = res.jsonPath();
        String Token = jsonPath.get("token");
        Utils.setEnvVar("Token", Token);
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test(priority = 4, description = "Get Item List")
    public void getItemList() {

        Response res = userController.getItemList();
        System.out.println(res.asString());
        Assert.assertEquals(res.getStatusCode(), 200);

    }

    @Test(priority = 5, description = "Add Item ")
    public void addItemPositive() throws org.apache.commons.configuration.ConfigurationException {

        AddItemModel addItemModel = new AddItemModel();

        addItemModel.setItemName("Postman");
        addItemModel.setQuantity("8");
        addItemModel.setAmount("20");
        addItemModel.setPurchaseDate("2025-08-07");
        addItemModel.setMonth("August");
        addItemModel.setRemarks("Item add");

        Response res = userController.addItem(addItemModel);
        Assert.assertEquals(res.getStatusCode(), 201);

        System.out.println(res.asString());
        JsonPath jsonPath = res.jsonPath();

        String itemID = jsonPath.get("_id");
        Utils.setEnvVar("ItemID", itemID);
        String itemName = jsonPath.get("itemName");
        Utils.setEnvVar("ItemName", itemName);

        getItemList();
    }

    @Test(priority = 6, description = "Edit Item Name ")
    public void editItem(){

        AddItemModel addItemModel = new AddItemModel();

       addItemModel.setItemName("Software");
        addItemModel.setQuantity("5");
       addItemModel.setAmount("30");
       addItemModel.setPurchaseDate("2025-08-07");
        addItemModel.setMonth("August");
        addItemModel.setRemarks("Updated item add");
        Response res = userController.editItem(prop.getProperty("ItemID"), addItemModel);
        System.out.println(res.asString());
        Assert.assertEquals(res.getStatusCode(), 200);

    }
    @Test(priority = 7, description = "Delete Item ")
    public void deleteItem(){

        Response res = userController.deleteItem(prop.getProperty("ItemID"));

        System.out.println(res.asString());
        Assert.assertEquals(res.getStatusCode(), 200);
    }
}
