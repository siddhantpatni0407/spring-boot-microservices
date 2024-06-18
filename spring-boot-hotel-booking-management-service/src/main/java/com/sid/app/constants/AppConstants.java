package com.sid.app.constants;

/**
 * @author Siddhant Patni
 */
public class AppConstants {

    /**
     * Below Constants are used for Endpoint.
     */
    public static final String ALL_USERS_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/users/all";
    public static final String USER_BY_ID_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/users/get-by-id";
    public static final String DELETE_USER_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/users/delete";
    public static final String LOGGED_IN_USER_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/users/get-logged-in-profile-info";
    public static final String USER_BOOKINGS_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/users/get-user-bookings";

    public static final String ADD_NEW_ROOM_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/rooms/add";
    public static final String ALL_ROOMS_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/rooms/all";
    public static final String GET_ROOM_TYPE_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/rooms/types";
    public static final String ROOM_BY_ID_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/rooms/get-by-id";
    public static final String ALL_AVAILABLE_ROOMS_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/rooms/all-available-rooms";
    public static final String AVAILABLE_ROOMS_BY_DATE_TYPE_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/rooms/available-rooms-by-date-and-type";
    public static final String UPDATE_ROOM_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/rooms/update";
    public static final String DELETE_ROOM_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/rooms/delete";

    public static final String BOOK_ROOM_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/bookings/book-room";
    public static final String ALL_BOOKINGS_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/bookings/all";
    public static final String GET_BOOKINGS_BY_CONFIRMATION_CODE_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/bookings/get-by-confirmation-code";
    public static final String CANCEL_BOOKING_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/bookings/cancel";

    public static final String REGISTER_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/auth/register";
    public static final String LOGIN_ENDPOINT = "/api/v1/hotel-booking-mgmt-service/auth/login";

    public static final String DEFAULT_USER = "DEFAULT_USER";
    public static final String SUCCESSFUL = "Successful";
    public static final String USER = "USER";
    public static final String USER_NAME_NOT_FOUND = "User Name Not Found";
    public static final String BOOKING_NOT_FOUND = "Booking Not Found";
    public static final String ROOM_NOT_FOUND = "Room Not Found";
    public static final String USER_NOT_FOUND = "User Not Found";

}