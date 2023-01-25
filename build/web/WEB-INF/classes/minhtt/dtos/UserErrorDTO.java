/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtt.dtos;

/**
 *
 * @author minhv
 */
public class UserErrorDTO {
    private String emailError;
    private String fullNameError;
    private String passwordError;
    private String phoneError;
    private String addressError;
    private String confirmError;
    private String statusError;

    public UserErrorDTO(String emailError, String fullNameError, String passwordError, String phoneError, String addressError, String confirmError, String statusError) {
        this.emailError = emailError;
        this.fullNameError = fullNameError;
        this.passwordError = passwordError;
        this.phoneError = phoneError;
        this.addressError = addressError;
        this.confirmError = confirmError;
        this.statusError = statusError;
    }
    
    public UserErrorDTO() {
    }
    

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getFullNameError() {
        return fullNameError;
    }

    public void setFullNameError(String fullNameError) {
        this.fullNameError = fullNameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getConfirmError() {
        return confirmError;
    }

    public void setConfirmError(String confirmError) {
        this.confirmError = confirmError;
    }

    public String getStatusError() {
        return statusError;
    }

    public void setStatusError(String statusError) {
        this.statusError = statusError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }

    public String getAddressError() {
        return addressError;
    }

    public void setAddressError(String addressError) {
        this.addressError = addressError;
    }
    
    
    
    
}
