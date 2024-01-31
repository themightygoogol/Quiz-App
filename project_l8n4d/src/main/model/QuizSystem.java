package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

/**
 * Represents a system containing all the users
 */
public class QuizSystem implements Writable {
    private final ArrayList<User> users;

    // EFFECTS: creates new quiz system with imputed list of users
    public QuizSystem(ArrayList<User> users) {
        this.users = users;
        User user = new User("public", "", new ArrayList<>());
        users.add(user);
    }

    // MODIFIES: this
    // EFFECTS: adds imputed user to the system and adds event to event log
    public void addUser(User user) {
        users.add(user);
        EventLog.getInstance().logEvent(new Event("User " + user.getUsername() + " added to Quiz App."));

    }

    // EFFECTS: returns number of users in the system
    public int getNumberOfUsers() {
        return this.users.size();
    }

    // REQUIRES: 0 <= i < users.size();
    // MODIFIES: this
    // EFFECTS: returns user at given index
    public User getUser(int i) {
        return users.get(i);
    }

    // EFFECTS: returns all users
    public ArrayList getUsers() {
        ArrayList listOfUsers = new ArrayList();
        for (int i = 0; i < users.size(); i++) {
            listOfUsers.add(users.get(i));
        }
        return listOfUsers;
    }

    // REQUIRES: user with imputed username must be in the quiz system
    // MODIFIES: this
    // EFFECTS: returns user with imputed username in the quiz system
    public User findUser(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return users.get(i);
            }
        }
        return null;
    }

    // REQUIRES: user with imputed username must be in the quiz system
    // MODIFIES: this
    // EFFECTS: removes user with imputed username in the quiz system and adds event to event log
    public void removeUser(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                users.remove(users.get(i));
            }
        }
        EventLog.getInstance().logEvent(new Event("User " + username + " removed from Quiz App."));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("users", usersToJson());
        return json;
    }

    // EFFECTS: returns users in this quiz system as a JSON array
    private JSONArray usersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (User u : users) {
            jsonArray.put(u.toJson());
        }
        return jsonArray;
    }
}
