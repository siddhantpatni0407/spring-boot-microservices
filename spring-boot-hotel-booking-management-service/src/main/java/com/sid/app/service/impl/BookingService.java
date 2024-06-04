package com.sid.app.service.impl;


import com.sid.app.constants.AppConstants;
import com.sid.app.dto.BookingDTO;
import com.sid.app.dto.Response;
import com.sid.app.entity.Booking;
import com.sid.app.entity.Room;
import com.sid.app.entity.User;
import com.sid.app.exception.CustomException;
import com.sid.app.repo.BookingRepository;
import com.sid.app.repo.RoomRepository;
import com.sid.app.repo.UserRepository;
import com.sid.app.service.interfac.IBookingService;
import com.sid.app.service.interfac.IRoomService;
import com.sid.app.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Response saveBooking(Long rooId, Long userId, Booking bookingRequest) {
        Response response = new Response();

        try {
            if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
                throw new IllegalArgumentException("Check in date must come before check out date");
            }
            Room room = roomRepository.findById(rooId).orElseThrow(() -> new CustomException(AppConstants.ROOM_NOT_FOUND));
            User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(AppConstants.USER_NOT_FOUND));

            List<Booking> existingBookings = room.getBookings();
            if (!roomIsAvailable(bookingRequest, existingBookings)) {
                throw new CustomException("Room not Available for the selected date range");
            }
            bookingRequest.setRoom(room);
            bookingRequest.setUser(user);
            String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
            bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
            bookingRepository.save(bookingRequest);

            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage(AppConstants.SUCCESSFUL);
            response.setBookingConfirmationCode(bookingConfirmationCode);


            response.setMessage(AppConstants.SUCCESSFUL);
            response.setStatusCode(HttpStatus.OK.value());

        } catch (CustomException e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error saving a  booking " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        Response response = new Response();

        try {
            Booking booking = bookingRepository
                    .findByBookingConfirmationCode(confirmationCode)
                    .orElseThrow(() -> new CustomException(AppConstants.BOOKING_NOT_FOUND));
            BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTOPlusBookedRooms(booking, true);
            response.setMessage(AppConstants.SUCCESSFUL);
            response.setStatusCode(HttpStatus.OK.value());
            response.setBooking(bookingDTO);

        } catch (CustomException e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error getting booking by confirmation code " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();

        try {
            List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDTO> bookingDTOList = Utils.mapBookingListEntityToBookingListDTO(bookingList);
            response.setMessage(AppConstants.SUCCESSFUL);
            response.setStatusCode(HttpStatus.OK.value());
            response.setBookingList(bookingDTOList);

        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error getting all bookings " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response cancelBooking(Long bookingId) {
        Response response = new Response();

        try {
            bookingRepository
                    .findById(bookingId)
                    .orElseThrow(() -> new CustomException(AppConstants.BOOKING_NOT_FOUND));
            bookingRepository.deleteById(bookingId);
            response.setMessage(AppConstants.SUCCESSFUL);
            response.setStatusCode(HttpStatus.OK.value());

        } catch (CustomException e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error cancelling a bookings " + e.getMessage());
        }
        return response;
    }

    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
        return existingBookings
                .stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );

    }

}