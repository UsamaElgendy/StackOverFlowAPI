package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersReceived {

    @SerializedName("items")
    private List<model.User> users;

    public List<model.User> getUsers() {
        return users;
    }

    public void setUsers(List<model.User> users) {
        this.users = users;
    }

}
