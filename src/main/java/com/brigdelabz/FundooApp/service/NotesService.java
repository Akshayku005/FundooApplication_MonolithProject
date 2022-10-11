package com.brigdelabz.FundooApp.service;


import com.brigdelabz.FundooApp.dto.NotesDto;
import com.brigdelabz.FundooApp.exception.UserException;
import com.brigdelabz.FundooApp.model.LabelModel;
import com.brigdelabz.FundooApp.model.Notes;
import com.brigdelabz.FundooApp.model.User;
import com.brigdelabz.FundooApp.repository.LabelRepository;
import com.brigdelabz.FundooApp.repository.NotesRepository;
import com.brigdelabz.FundooApp.repository.UserRepository;
import com.brigdelabz.FundooApp.util.EmailSenderService;
import com.brigdelabz.FundooApp.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotesService implements INotesService {

    @Autowired
    NotesRepository notesRepository;
    @Autowired
    LabelRepository labelRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenUtility util;

    @Autowired
    EmailSenderService mailService;

    @Override
    public Notes createNote(NotesDto notesDto, String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Notes newNotes = new Notes(notesDto);
            newNotes.setUserId(userId);
            newNotes.setLabelId(notesDto.getLabelId());
            notesRepository.save(newNotes);
            mailService.sendEmail(user.get().getEmail(), "Notes added ", "your notes added successfully with your userId "
                    + userId);
            return newNotes;
        }
        throw new UserException(HttpStatus.FOUND, "Invalid token");
    }

//    @Override
//    public Notes addLabel(Long notesId, Long labelId, String token) {
//        Long userId = util.decodeToken(token);
//        Optional<User> user = userRepository.findById(userId);
//        if (user.isPresent()) {
//            Optional<Notes>notes=notesRepository.findByUserIdAndNotesId(userId, notesId);
//            if (notes.isPresent()){
//                Optional<LabelModel>labelModel=labelRepository.findById(labelId);
//                if (labelModel.isPresent()){
//                    Notes newNotes = new Notes(notes.get());
//                    newNotes.setUserId(userId);
//                    newNotes.setLabelId(labelId);
//                    notesRepository.save(newNotes);
//                    return newNotes;
//                }
//                throw new UserException(HttpStatus.FOUND, "label not found");
//            }
//            throw new UserException(HttpStatus.FOUND, "Notes not found using user id and notes id");
//        }
//        throw new UserException(HttpStatus.FOUND, "Invalid token");
//    }

    @Override
    public Notes updateNote(NotesDto notesDto, Long notesId, String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Notes> notes = notesRepository.findById(notesId);
            if (userId.equals(notes.get().getUserId())) {
                Notes updatedNotes = new Notes(notesDto);
                updatedNotes.setUserId(userId);
                notesRepository.save(updatedNotes);
                mailService.sendEmail(user.get().getEmail(), "Notes Updated ", "your notes Updated successfully with your userId "
                        + userId);
                return updatedNotes;
            } else
                throw new UserException(HttpStatus.FOUND, "user Id not match with Notes user Id");
        } else
            throw new UserException(HttpStatus.FOUND, "User not found");
    }

    @Override
    public List<Notes> readAllNotes(String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Notes> readAllNotes = notesRepository.findByUserId(userId);
            if (readAllNotes.size() > 0) {
                return readAllNotes;
            }
            throw new UserException(HttpStatus.FOUND, "No data found");
        }
        throw new UserException(HttpStatus.FOUND, "Invalid token");
    }

    @Override
    public Optional<Notes> readNotesById(Long notesId, String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Notes> isNotesPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNotesPresent.isPresent()) {
                return isNotesPresent;
            }
            throw new UserException(HttpStatus.FOUND, "notes not present");
        }
        throw new UserException(HttpStatus.FOUND, "Invalid token");
    }

    @Override
    public Notes archieveNoteById(Long notesId, String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Notes> isNotesPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNotesPresent.isPresent()) {
                isNotesPresent.get().setArchieve(true);
                notesRepository.save(isNotesPresent.get());
                return isNotesPresent.get();
            }
            throw new UserException(HttpStatus.FOUND, "notes not present");
        }
        throw new UserException(HttpStatus.FOUND, "Invalid token");
    }

    @Override
    public Notes unArchieveNoteById(Long notesId, String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Notes> isNotesPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNotesPresent.isPresent()) {
                isNotesPresent.get().setArchieve(false);
                notesRepository.save(isNotesPresent.get());
                return isNotesPresent.get();
            }
            throw new UserException(HttpStatus.FOUND, "notes not present");
        }
        throw new UserException(HttpStatus.NOT_FOUND, "Invalid token");
    }

    @Override
    public Notes trashNote(Long notesId, String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Notes> isNotesPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNotesPresent.isPresent()) {
                isNotesPresent.get().setTrash(true);
                notesRepository.save(isNotesPresent.get());
                return isNotesPresent.get();
            }
            throw new UserException(HttpStatus.FOUND, "notes not present");
        }
        throw new UserException(HttpStatus.NOT_FOUND, "Token not find");
    }

    @Override
    public Notes restoreNote(Long notesId, String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Notes> isNotesPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNotesPresent.isPresent()) {
                isNotesPresent.get().setTrash(false);
                notesRepository.save(isNotesPresent.get());
                return isNotesPresent.get();
            }
            throw new UserException(HttpStatus.FOUND, "notes not present");
        }
        throw new UserException(HttpStatus.NOT_FOUND, "Invalid token");
    }

    @Override
    public Notes deleteNote(Long notesId, String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Notes> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNoteAndUserIdPresent.isPresent() && isNoteAndUserIdPresent.get().isTrash() == true) {
                notesRepository.delete(isNoteAndUserIdPresent.get());
                return isNoteAndUserIdPresent.get();
            }
        }
        throw new UserException(HttpStatus.FOUND, "Invalid token");
    }

    @Override
    public Notes changeNoteColor(Long notesId, String color, String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Notes> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNoteAndUserIdPresent.isPresent()) {
                isNoteAndUserIdPresent.get().setColor(color);
                notesRepository.save(isNoteAndUserIdPresent.get());
                return isNoteAndUserIdPresent.get();
            }
        }
        throw new UserException(HttpStatus.NOT_FOUND, "Invalid token");
    }

    @Override
    public Notes pinNote(Long notesId, String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Notes> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNoteAndUserIdPresent.isPresent()) {
                isNoteAndUserIdPresent.get().setPin(true);
                notesRepository.save(isNoteAndUserIdPresent.get());
                return isNoteAndUserIdPresent.get();
            }
        }
        throw new UserException(HttpStatus.NOT_FOUND, "Invalid token");
    }

    @Override
    public Notes unPinNote(Long notesId, String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {

            Optional<Notes> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNoteAndUserIdPresent.isPresent()) {
                isNoteAndUserIdPresent.get().setPin(false);
                notesRepository.save(isNoteAndUserIdPresent.get());
                return isNoteAndUserIdPresent.get();
            }
        }
        throw new UserException(HttpStatus.NOT_FOUND, "Invalid token");
    }


    @Override
    public List<Notes> getAllPinnedNotes(String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Notes> userList = notesRepository.findAllByPin();
            if (userList.size() > 0) {
                return userList;
            }
        }
        throw new UserException(HttpStatus.NOT_FOUND, "Invalid token");
    }

    @Override
    public List<Notes> getAllArchievedNotes(String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Notes> userList = notesRepository.findAllByisArchieve();
            if (userList.size() > 0) {
                return userList;
            }
        }
        throw new UserException(HttpStatus.NOT_FOUND, "Invalid token");
    }

    @Override
    public List<Notes> getAllTrashNotes(String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Notes> userList = notesRepository.findAllByTrash();
            if (userList.size() > 0) {
                return userList;
            }
        }
        throw new UserException(HttpStatus.NOT_FOUND, "Invalid token");
    }

    @Override
    public Notes notesLabelList(Long notesId, Long labelId, String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Notes> notes = notesRepository.findById(notesId);
            if (notes.get().getUserId() == userId) {
                Optional<LabelModel> labelModel = labelRepository.findById(labelId);
                List<LabelModel> labelList = new ArrayList<>();
                List<Notes> notesList = new ArrayList<>();
                if (labelModel.isPresent()) {
                    labelList.add(labelModel.get());
                    notes.get().setLabelList(labelList);
                    notesRepository.save(notes.get());

                    notesList.add(notes.get());
                    labelModel.get().setNotes(notesList);
                    labelModel.get().setNoteId(notesId);
                    labelModel.get().setUserId(userId);
                    labelRepository.save(labelModel.get());
                    return notes.get();
                }
                throw new UserException(HttpStatus.FOUND, "label not found");
            }
            throw new UserException(HttpStatus.FOUND, "User Id and notes user Id not match");
        }
        throw new UserException(HttpStatus.FOUND, "User not found ");
    }

    @Override
    public Notes addCollaborator(Long notesId, String collaborator, String token) {
        Long userId = util.decodeToken(token);
        Optional<User> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<Notes> isNoteAndUserId = notesRepository.findByUserIdAndNotesId(userId, notesId);
            List<String> collaboratorList = new ArrayList<>();
            if (isNoteAndUserId.isPresent()) {
                Optional<User> isUserEmailPresent = userRepository.findByEmail(collaborator);
                if (isUserEmailPresent.isPresent()) {
                    collaboratorList.add(collaborator);
                    isNoteAndUserId.get().setCollaborator(collaboratorList);
                    notesRepository.save(isNoteAndUserId.get());
                    return isNoteAndUserId.get();
                }
                throw new UserException(HttpStatus.FOUND, "Collaborator email Id not found in DB");
            }
            throw new UserException(HttpStatus.FOUND, "Notes Id with User Id not found");
        }
        throw new UserException(HttpStatus.FOUND, "Token not found");
    }
@Override
    public Optional<Notes> readNotesByCollaborator(Long notesId, String email) {
        Optional<Notes> isNotesPresent = notesRepository.findById(notesId);
        if (isNotesPresent.isPresent()) {
            Optional<User> user = userRepository.findByEmail(email);
            List<String> collaborator = isNotesPresent.get().getCollaborator();
            for (String list : collaborator) {
                if (list.equals(email)) {
                    System.out.println(list);
                    return isNotesPresent;
                }
                throw new UserException(HttpStatus.FOUND, "given email not collaboratored");
            }
        }
        throw new UserException(HttpStatus.FOUND, "notes not present");
    }


    @Override
    public Notes setRemainderTime(String remainderTime, String token, Long notesId) {
        Long userId = util.decodeToken(token);
        Optional<User> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<Notes> isNotesPresent = notesRepository.findById(notesId);
            if (isNotesPresent.isPresent()) {
                LocalDate today = LocalDate.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                LocalDate remainder = LocalDate.parse(remainderTime, dateTimeFormatter);
                if (remainder.isBefore(today)) {
                    throw new UserException(HttpStatus.FOUND, "invalid remainder time");
                } else {
                    isNotesPresent.get().setRemindertime(remainderTime);
                    notesRepository.save(isNotesPresent.get());
                    return isNotesPresent.get();
                }
            }
            throw new UserException(HttpStatus.FOUND, "notes not present with this NotesId");
        }
        throw new UserException(HttpStatus.FOUND, "Invalid token");
    }
}