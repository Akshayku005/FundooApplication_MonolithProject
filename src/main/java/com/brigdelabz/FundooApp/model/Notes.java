package com.brigdelabz.FundooApp.model;

import com.brigdelabz.FundooApp.dto.NotesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long notesId;
	
	private String title;

	private String description;

	private long userId;

	private LocalDateTime registerDate = LocalDateTime.now() ;

	private LocalDateTime updateDate;
	private boolean Trash;
	private boolean isArchieve;

	private boolean pin;

	private Long labelId;

	private String emailId;

	private String color;

	private String remindertime;


	@ManyToMany(cascade = CascadeType.ALL)
	private List<LabelModel> labelList;

	@ElementCollection(targetClass = String.class)
	private List<String> collaborator;

	public Notes(NotesDto notesDto) {
		this.title = notesDto.getTitle();
		this.description = notesDto.getDescription();
		this.labelId=notesDto.getLabelId();
		this.emailId = notesDto.getEmailId();
		this.color = notesDto.getColor();

	}

	public Notes(Notes notes) {
	}
}