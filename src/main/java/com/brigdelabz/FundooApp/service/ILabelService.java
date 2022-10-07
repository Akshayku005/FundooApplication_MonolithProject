package com.brigdelabz.FundooApp.service;


import com.brigdelabz.FundooApp.dto.LabelDto;
import com.brigdelabz.FundooApp.dto.ResponseDTO;
import com.brigdelabz.FundooApp.model.LabelModel;

import java.util.List;

public interface ILabelService {

	LabelModel addLabel(LabelDto labelDto, String token);

	LabelModel updateLabel(LabelDto labelDto, Long id, String token);

	List<LabelModel> getAllLabels(String token);

	ResponseDTO deleteLabel(Long id, String token);
}