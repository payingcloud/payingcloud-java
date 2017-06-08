package cn.payingcloud.model;

import java.util.List;

/**
 * @author YQ.Huang
 */
public class PcError implements PcResponse {

    public static class ValidationError {
        private String field;
        private Object rejected;
        private String message;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Object getRejected() {
            return rejected;
        }

        public void setRejected(Object rejected) {
            this.rejected = rejected;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    private String error;
    private String errorDescription;
    private String errorUri;
    private List<ValidationError> validationErrors;

    public boolean isSuccessful() {
        return error == null;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorUri() {
        return errorUri;
    }

    public void setErrorUri(String errorUri) {
        this.errorUri = errorUri;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

}
