package com.iceteaviet.englishnow.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Genius Doan on 28/12/2017.
 */

public class RegisterRequest {
    private RegisterRequest() {

    }

    public static class ServerRegisterRequest {
        @Expose
        @SerializedName("email")
        private String email;

        @Expose
        @SerializedName("username")
        private String username;

        @Expose
        @SerializedName("password")
        private String password;

        public ServerRegisterRequest(String email, String username, String password) {
            this.email = email;
            this.username = username;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;

            ServerRegisterRequest that = (ServerRegisterRequest) object;

            if (email != null ? !email.equals(that.email) : that.email != null) return false;
            return password != null ? password.equals(that.password) : that.password == null;

        }

        @Override
        public int hashCode() {
            int result = email != null ? email.hashCode() : 0;
            result = 31 * result + (password != null ? password.hashCode() : 0);
            return result;
        }
    }
}
