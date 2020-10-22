package com.shahdivya.stoxriser.models;

public class loginParams{
    private Integer id;
    private String uniqueId;
    private String name;
    private String email;
    private String encryptedPassword;
    private String salt;
    private String createdAt;
    private String updatedAt;
    public loginParams(Integer id, String uniqueId, String name, String email, String encryptedPassword, String salt, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.uniqueId = uniqueId;
        this.name = name;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.salt = salt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
