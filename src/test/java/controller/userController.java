package controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import setup.AddItemModel;
import setup.UserModel;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class userController {
    Properties prop;
    public userController(Properties prop){
        this.prop = prop;
    }
    public Response doLogin(UserModel userModel){
        RestAssured.baseURI = prop.getProperty("baseURL");
        return  given().contentType("application/json").body(userModel)
                .when().post("api/auth/login");
    }
    public Response getUserList(){

        RestAssured.baseURI= prop.getProperty("baseURL");

        return given().contentType("application/json").
                header("Authorization","Bearer "+prop.getProperty("adminToken"))
                .when()
                .get("/api/user/users");
    }
    public Response searchUser(String id){

        RestAssured.baseURI= prop.getProperty("baseURL");

        return given().contentType("application/json").
                header("Authorization","Bearer "+prop.getProperty("adminToken"))
                .when()
                .get("/api/user/"+id);

    }
    public Response editUserInfo(String userid, UserModel userModel){

        RestAssured.baseURI= prop.getProperty("baseURL");

        return given().contentType("application/json").body(userModel)
                .header("Authorization","Bearer "+ prop.getProperty("adminToken"))
                .when()
                .put("/api/user/"+ userid);

    }
    public Response doRegister(UserModel userModel){
        RestAssured.baseURI=prop.getProperty("baseURL");
        return given().contentType("application/json")
                .body(userModel).when().post("/api/auth/register");
    }
    public Response getItemList(){
      RestAssured.baseURI=prop.getProperty("baseURL");
      return given().contentType("application/json")
              .header("Authorization","Bearer "+prop.getProperty("Token"))
              .when().get("/api/costs");
   }
   public Response addItem(AddItemModel addItemModel){
       RestAssured.baseURI=prop.getProperty("baseURL");
       return given().contentType("application/json")
               .body(addItemModel)
               .header("Authorization","Bearer "+prop.getProperty("Token"))
               .when()
               .post("/api/costs");
   }
    public Response editItem(String itemID, AddItemModel addItemModel){

        RestAssured.baseURI= prop.getProperty("baseURL");

        return given().contentType("application/json")
                .body(addItemModel)
                .header("Authorization","Bearer "+ prop.getProperty("Token"))
                .when()
                .put("/api/costs/"+ itemID);

    }
    public Response deleteItem(String itemID){

        RestAssured.baseURI = prop.getProperty("baseURL");

        return given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("Token"))
                .when()
                .delete("/api/costs/"+itemID);
    }

}
