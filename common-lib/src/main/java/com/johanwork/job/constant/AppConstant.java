package com.johanwork.job.constant;

public class AppConstant {
    private AppConstant() {
    }

    public static class Success {
        public static final String FETCHED = "%s fetched successfully";
        public static final String CREATED = "%s created successfully";
        public static final String UPDATED = "%s updated successfully";
        public static final String DELETED = "%s deleted successfully";
        public static final String DELETED_ALL = "%s deleted All successfully";
        public static final String SIGNUP = "You have successfully signed up.";
        public static final String LOGIN = "You have successfully login.";
        public static final String UPLOAD = "Upload file successfully";
        public static final String IS_SAVED_JOB = "You have successfully to saved this job";
        public static final String IS_UNSAVED_JOB = "You have successfully to unsaved this job";
        private Success(){}
    }

    public static final class Error{
        public static final String TITLE_NOT_FOUND = "%s NOT FOUND";
        public static final String MESSAGE_NOT_FOUND = "%s not found with id: %s";
        public static final String TITLE_ALREADY_EXISTS = "%s ALREADY EXISTS";
        public static final String MESSAGE_ALREADY_EXISTS = "%s already exists";

        public static final String TITLE_BAD_REQUEST = "VALIDATION ERROR";
        public static final String MESSAGE_BAD_REQUEST = "Bad request make sure data is valid";

        public static final String TITLE_INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";
        public static final String MESSAGE_INTERNAL_SERVER_ERROR = "An error occurred. Please try again or contact Dev Team";

        public static final String TITLE_BAD_CREDENTIALS = "BAD CREDENTIALS";
        public static final String MESSAGE_BAD_CREDENTIALS = "Invalid Password";

        public static final String TITLE_TOKEN_EXPIRED = "TOKEN EXPIRED";
        public static final String MESSAGE_TOKEN_EXPIRED = "Token has expired, please login again";

        public static final String TITLE_FAILED_UPLOAD = "FAILED UPLOAD";
        public static final String MESSAGE_FAILED_UPLOAD = "Failed to upload file in Oracle Object Storage";

        public static final String TITLE_FORBIDDEN = "FORBIDDEN";
        public static final String MESSAGE_FORBIDDEN = "You are not authorized to access this resource";

        public static final String TITLE_DUPLICATE = "DUPLICATE BARCODE";
        public static final String MESSAGE_DUPLICATE = "%s already exists in barcode %s";

        public static final String TITLE_INFISICAL_UNAVAILABLE = "INFISICAL UNAVAILABLE";
        public static final String MESSAGE_INFISICAL_UNAVAILABLE = "Infisical is unavailable, please try again later";

        public static final String TITLE_JOB_EXPIRED = "JOB EXPIRED";
        public static final String MESSAGE_JOB_EXPIRED = "Job has expired";

        public static final String TITLE_APPLICATION_ALREADY_EXISTS = "APPLICATION ALREADY EXISTS";
        public static final String MESSAGE_APPLICATION_ALREADY_EXISTS = "You have already applied for this job";

        public static final String TITLE_ALREADY_WITHDRAWN = "APPLICATION ALREADY WITHDRAWN";
        public static final String MESSAGE_ALREADY_WITHDRAWN = "You have already withdrawn this application";

        public static final String TITLE_JOB_ALREADY_SAVED = "JOB ALREADY SAVED";
        public static final String MESSAGE_JOB_ALREADY_SAVED = "You have already saved this job";

        private Error() {}
    }

}