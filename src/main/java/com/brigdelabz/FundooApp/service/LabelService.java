package com.brigdelabz.FundooApp.service;


import com.brigdelabz.FundooApp.dto.LabelDto;
import com.brigdelabz.FundooApp.dto.ResponseDTO;
import com.brigdelabz.FundooApp.exception.UserException;
import com.brigdelabz.FundooApp.model.LabelModel;
import com.brigdelabz.FundooApp.model.User;
import com.brigdelabz.FundooApp.repository.LabelRepository;
import com.brigdelabz.FundooApp.repository.UserRepository;
import com.brigdelabz.FundooApp.util.EmailSenderService;
import com.brigdelabz.FundooApp.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LabelService implements ILabelService {

    @Autowired
    LabelRepository labelRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenUtility tokenUtil;

    @Autowired
    EmailSenderService mailService;


    //Purpose:Service to add label
    @Override
    public LabelModel addLabel(LabelDto labelDto, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<User> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            LabelModel model = new LabelModel(labelDto);
            model.setUserId(userId);
            labelRepository.save(model);
            return model;
        }
        throw new UserException(HttpStatus.FOUND, "Token Invalid");
    }

    //Purpose:Service to update label
    @Override
    public LabelModel updateLabel(LabelDto labelDto, Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<User> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<LabelModel> isLabelPresent = labelRepository.findById(id);
            if (isLabelPresent.isPresent()) {
                isLabelPresent.get().setLabelName(labelDto.getLabelName());
                isLabelPresent.get().setUpdateDate(LocalDateTime.now());
                labelRepository.save(isLabelPresent.get());
                return isLabelPresent.get();
            }
            throw new UserException(HttpStatus.FOUND, "Label not present");
        }
        throw new UserException(HttpStatus.FOUND, "Token Invalid");
    }

    //Purpose:Service to fetch all the labels
    @Override
    public List<LabelModel> getAllLabels(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<User> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            List<LabelModel> getAllLabels = labelRepository.findAll();
            if (getAllLabels.size() > 0) {
                return getAllLabels;
            } else {
                throw new UserException(HttpStatus.FOUND, "Label not present");
            }
        }
        throw new UserException(HttpStatus.FOUND, "Token Invalid");
    }

    //Purpose:Method to delete the label
    @Override
    public ResponseDTO deleteLabel(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<User> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<LabelModel> isIdPresent = labelRepository.findById(id);
            if (isIdPresent.isPresent()) {
                labelRepository.deleteById(id);
                return new ResponseDTO("Successfully deleted", isIdPresent.get());
            } else {
                throw new UserException(HttpStatus.FOUND, "Label not found");
            }
        }
        throw new UserException(HttpStatus.FOUND, "Invalid token");
    }

}
