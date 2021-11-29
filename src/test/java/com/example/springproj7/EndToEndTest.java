package com.example.springproj7;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class Tests {

    String json = new String(Files.readAllBytes(Paths.get("./firebase.json")));
    JSONObject key = new JSONObject(json);

    String EMAIL = key.getString("FIREBASE_EMAIL");
    String PASSWORD = key.getString("FIREBASE_PASSWORD");
    String FIREBASE_WEB_API_KEY = key.getString("FIREBASE_WEB_API_KEY");
    String FIREBASE_SIGNIN_URL = key.getString("FIREBASE_SIGNIN_URL");
    String FIRESTORE_USERS_COLLECTION_URL = key.getString("FIRESTORE_USERS_COLLECTION_URL");

    Tests() throws JSONException, IOException {
    }

    @Test
    public void test() throws IOException, InterruptedException, JSONException {
        JSONObject authData = new JSONObject();

        authData.put("email", EMAIL);
        authData.put("password", PASSWORD);
        authData.put("returnSecureToken", "true");

        String authRes = UtilityFunctions.post(FIREBASE_SIGNIN_URL + FIREBASE_WEB_API_KEY, authData);

        JSONObject authResJson = new JSONObject(authRes);
        String idToken = authResJson.getString("idToken");
        String userId = authResJson.getString("localId");

        String userAvatarUrl = String.format("%s/%s?updateMask.fieldPaths=avatar", FIRESTORE_USERS_COLLECTION_URL, userId);

        JSONObject data = new JSONObject();
        JSONObject fieldsObject = new JSONObject();
        JSONObject avatarObject = new JSONObject();

        avatarObject.put("stringValue", "");
        fieldsObject.put("avatar", avatarObject);
        data.put("fields", fieldsObject);

        UtilityFunctions.patch(userAvatarUrl, idToken, data.toString());

        String userUrl = String.format("%s/%s", FIRESTORE_USERS_COLLECTION_URL, userId);
        String userDetails = UtilityFunctions.get(userUrl, idToken);
        String avatarLink = new JSONObject(userDetails).getJSONObject("fields").getJSONObject("avatar").getString("stringValue");
        if (!avatarLink.isEmpty()) fail();

        idToken = "";

        String userDetailsLoggedOut = UtilityFunctions.get(userUrl, idToken);

        assertEquals("Forbidden", userDetailsLoggedOut);
    }
}