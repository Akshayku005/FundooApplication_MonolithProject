package com.brigdelabz.FundooApp.repository;

import com.brigdelabz.FundooApp.model.LabelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<LabelModel,Long> {
}
