package com.brigdelabz.FundooApp.model;

import com.brigdelabz.FundooApp.dto.LabelDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "label")
@Data
@NoArgsConstructor
public class LabelModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String labelName;

	private Long userId;
	
	private Long noteId;
	
	private LocalDateTime registerDate=LocalDateTime.now();

	private LocalDateTime updateDate;
	@JsonIgnore
	@ManyToMany
	private List<Notes> notes;

	public LabelModel(LabelDto labelDto) {
		this.labelName = labelDto.getLabelName();
	}

	public LabelModel(LabelModel labelModel) {
	}
}