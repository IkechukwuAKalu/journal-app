package com.ikechukwuakalu.journalapp.data.models;

public class User {

    private String id;
    private String name;
    private String email;
    private String photoUri;

    public User(String id, String name, String email, String photoUri) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.photoUri = photoUri;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return (getId().equals(user.getId()) &&
                getName().equals(user.getName())) &&
                (getEmail().equals(user.getEmail())) &&
                (getPhotoUri().equals(user.getPhotoUri()));
    }

    @Override
    public String toString() {
        return "User(id: " + getId() +
                " name: " + getName() +
                " email: " + getEmail() +
                " photoUri: " + getPhotoUri() + ")";
    }
}
