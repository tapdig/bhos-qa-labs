package com.example.springproj6;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Tests {

    String json = new String(Files.readAllBytes(Paths.get("./firebase.json")));
    JSONObject key = new JSONObject(json);

    String EMAIL = key.getString("FIREBASE_EMAIL");
    String PASSWORD = key.getString("FIREBASE_PASSWORD");
    String FIREBASE_WEB_API_KEY = key.getString("FIREBASE_WEB_API_KEY");
    String FIREBASE_SIGNIN_URL = key.getString("FIREBASE_SIGNIN_URL");
    String FIRESTORE_USERS_COLLECTION_URL = key.getString("FIRESTORE_USERS_COLLECTION_URL");
    String FIREBASE_STORAGE = key.getString("FIREBASE_STORAGE");
    String FIREBASE_ACCESS_TOKEN = key.getString("FIREBASE_ACCESS_TOKEN");

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

        String uploadUrl = String.format("%s/%s%%2F%s?alt=media&token=%s", FIREBASE_STORAGE, userId, "avatar.jpg", FIREBASE_ACCESS_TOKEN);
        String uploadedFile = UtilityFunctions.uploadFile(uploadUrl, idToken, "avatar.jpg");
        String fileReference = new JSONObject(uploadedFile).getString("name");

        String avatarFieldUrl = String.format("%s/%s?updateMask.fieldPaths=avatar", FIRESTORE_USERS_COLLECTION_URL, userId);
        JSONObject avatarData = new JSONObject();
        JSONObject fieldsObject = new JSONObject();
        JSONObject avatarObject = new JSONObject();
        avatarObject.put("stringValue", fileReference);
        fieldsObject.put("avatar", avatarObject);
        avatarData.put("fields", fieldsObject);

        UtilityFunctions.patch(avatarFieldUrl, idToken, avatarData.toString());

        String usersUrl = String.format("%s/%s", FIRESTORE_USERS_COLLECTION_URL, userId);
        String userDetails = UtilityFunctions.get(usersUrl, idToken);
        String avatarReference = new JSONObject(userDetails).getJSONObject("fields").getJSONObject("avatar").getString("stringValue");
        String avatarReferenceUrlEncoded = URLEncoder.encode(avatarReference, StandardCharsets.UTF_8.toString());

        String url = String.format("%s/%s?alt=media&token=%s", FIREBASE_STORAGE, avatarReferenceUrlEncoded, FIREBASE_ACCESS_TOKEN);

        assertEquals(200, UtilityFunctions.check(url, idToken));
    }
}