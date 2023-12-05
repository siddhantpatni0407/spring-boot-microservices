package com.sid.sb.jwt.app.contants;

/**
 * @author Siddhant Patni
 */
public class AppConstants {

    /**
     * The constant ENDPOINT.
     */
    public static final String USER_AUTHENTICATION_ENDPOINT = "/api/v1/user/authenticate";

    public static final String USER_REGISTER_ENDPOINT = "/api/v1/user/register";

    public static final String USER_PROFILE_ENDPOINT = "/api/v1/user/profile";

    public static final String ADMIN_USER_LIST_ENDPOINT = "/api/v1/admin/user-list";

    public static final String ADMIN_ROLE_LIST_ENDPOINT = "/api/v1/admin/role-list";

    public static final String JASYPT_SALT_GENERATOR_CLASS_NAME = "org.jasypt.salt.ZeroSaltGenerator";

    public static final String JASYPT_ALGORITHM = "PBEWithMD5AndDES";
}