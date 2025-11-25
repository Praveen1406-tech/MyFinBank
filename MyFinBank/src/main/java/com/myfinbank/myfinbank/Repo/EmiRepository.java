package com.myfinbank.myfinbank.Repo;

import com.myfinbank.myfinbank.model.EMIHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmiRepository extends JpaRepository<EMIHistory, Long> {
	

}
