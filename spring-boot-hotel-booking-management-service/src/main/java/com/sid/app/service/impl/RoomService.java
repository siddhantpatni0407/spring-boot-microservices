package com.sid.app.service.impl;

import com.sid.app.constants.AppConstants;
import com.sid.app.dto.Response;
import com.sid.app.dto.RoomDTO;
import com.sid.app.entity.Room;
import com.sid.app.exception.CustomException;
import com.sid.app.repo.BookingRepository;
import com.sid.app.repo.RoomRepository;
import com.sid.app.service.AwsS3Service;
import com.sid.app.service.interfac.IRoomService;
import com.sid.app.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private AwsS3Service awsS3Service;


    @Override
    public Response addNewRoom(String photoUrl, String roomType, BigDecimal roomPrice, String description) {
        Response response = new Response();

        try {

            //String imageUrl = awsS3Service.saveImageToS3(photoUrl);
            Room room = new Room();

            room.setRoomPhotoUrl(photoUrl);
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(description);

            Room savedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);

            response.setRoom(roomDTO);
            response.setMessage(AppConstants.SUCCESSFUL);
            response.setStatusCode(HttpStatus.OK.value());

        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error saving a room: " + e.getMessage());
        }
        return response;
    }


    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();

        try {
            List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);

            response.setMessage(AppConstants.SUCCESSFUL);
            response.setStatusCode(HttpStatus.OK.value());
            response.setRoomList(roomDTOList);

        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error getting all rooms " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        Response response = new Response();

        try {
            roomRepository.findById(roomId).orElseThrow(() -> new CustomException("Room Not Found"));
            roomRepository.deleteById(roomId);

            response.setMessage(AppConstants.SUCCESSFUL);
            response.setStatusCode(HttpStatus.OK.value());

        } catch (CustomException e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error deleting a room " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, String photoUrl) {
        Response response = new Response();

        try {
            //String imageUrl = null;

            /*if (photoUrl != null && !photoUrl.isEmpty()){
                imageUrl = awsS3Service.saveImageToS3(photoUrl);
            }*/

            Room room = roomRepository.findById(roomId).orElseThrow(() -> new CustomException(AppConstants.ROOM_NOT_FOUND));
            if (roomType != null) room.setRoomType(roomType);
            if (roomPrice != null) room.setRoomPrice(roomPrice);
            if (description != null) room.setRoomDescription(description);
            if (photoUrl != null) room.setRoomPhotoUrl(photoUrl);

            Room updatedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);

            response.setMessage(AppConstants.SUCCESSFUL);
            response.setStatusCode(HttpStatus.OK.value());
            response.setRoom(roomDTO);

        } catch (CustomException e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error updating a room " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();

        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new CustomException("Room Not Found"));
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTOPlusBookings(room);

            response.setMessage(AppConstants.SUCCESSFUL);
            response.setStatusCode(HttpStatus.OK.value());
            response.setRoom(roomDTO);

        } catch (CustomException e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error Getting a room By Id " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();

        try {
            List<Room> availableRooms = roomRepository.findAvailableRoomsByDateAndTypes(checkInDate, checkOutDate, roomType);
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(availableRooms);

            response.setMessage(AppConstants.SUCCESSFUL);
            response.setStatusCode(HttpStatus.OK.value());
            response.setRoomList(roomDTOList);

        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error getting available rooms " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        Response response = new Response();

        try {
            List<Room> roomList = roomRepository.getAllAvailableRooms();
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setMessage(AppConstants.SUCCESSFUL);
            response.setStatusCode(HttpStatus.OK.value());
            response.setRoomList(roomDTOList);

        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error getting available rooms " + e.getMessage());

        }
        return response;
    }

}