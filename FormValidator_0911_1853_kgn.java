// 代码生成时间: 2025-09-11 18:53:00
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * FormValidator is a Spring Boot component that implements the Validator interface.
 * It is used to validate form data and handle errors accordingly.
 */
@Component
public class FormValidator implements Validator {

    /*
     * Validate the form data. If the data is invalid, add error messages to the Errors object.
     * @param target The object to validate (the form data).
     * @param errors The Errors object to which error messages are added.
     */
    @Override
    public boolean supports(Class<?> clazz) {
        // This validator supports only the specified class.
        return FormData.class.isAssignableFrom(clazz);
    }

    /*
     * Validate the form data and add error messages if necessary.
     * @param target The object to validate (the form data).
     * @param errors The Errors object to which error messages are added.
     * @return true if the form data is valid, false otherwise.
     */
    @Override
    public boolean validate(Object target, Errors errors) {
        FormData formData = (FormData) target;

        // Check if the field 'name' is empty.
        if (formData.getName() == null || formData.getName().trim().isEmpty()) {
            errors.rejectValue("name", "name.required", "Name is required.");
            return false;
        }

        // Check if the field 'email' is empty or not a valid email address.
        if (formData.getEmail() == null || formData.getEmail().trim().isEmpty()) {
            errors.rejectValue("email", "email.required", "Email is required.");
            return false;
        } else if (!formData.getEmail().contains("@")) {
            errors.rejectValue("email", "email.invalid", "Invalid email address.");
            return false;
        }

        // Add more validation rules as needed.

        // If no errors were found, return true.
        return errors.hasErrors();
    }
}

class FormData {
    private String name;
    private String email;

    // Getters and setters for the fields.
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
}
